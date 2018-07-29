package com.microservice.demo.consumer1.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.microservice.demo.consumer1.domain.GreetingConsumerMessage;
import com.microservice.demo.consumer1.repository.GreetingMessageRepository;
import com.microservice.demo.producer1.domain.GreetingProducerMessage;

@Component
public class GreetingMessageListener {
	
	public static final String TOPIC = "topic-demo";
	
	private GreetingMessageRepository repository;
	
	@Autowired
	public void setRepository(GreetingMessageRepository repository) {
		this.repository = repository;
	}
	
	// consumiendo solo la data
//	@KafkaListener(topics = TOPIC, groupId = "${spring.kafka.consumer.group-id}")
//	@Transactional
	public void listenGreetingMessage(@Payload GreetingProducerMessage producer) {
		GreetingConsumerMessage consumer = 
				GreetingConsumerMessage
				.builder()
				.uuid(producer.getUuid())
				.description(producer.getDescription()).build();
		repository.save(consumer);
		System.out.println("Saved " + consumer.toString());
		throw new RuntimeException("Error for queque");
	}
	
	// consumiendo un objeto ConsumerRecord
	@KafkaListener(topics = TOPIC, groupId = "${spring.kafka.consumer.group-id}")
	@Transactional
	public void listenConsumerGreetingMessage(ConsumerRecord<?, ?> record) {
		System.out.println("Topic: "+record.topic());
		System.out.println("Partition: "+record.partition());
		System.out.println("Offset: "+record.offset());
		record.headers().forEach(header -> {System.out.println(header.key() + " - " + header.value());});
		
		Object key = record.key();
		Object value = record.value();
		System.out.println("Key: "+key);
		System.out.println("Value: "+value.toString());
		
		throw new RuntimeException("Error for queque");
	}
}
