package com.microservice.demo.consumer1.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.microservice.demo.consumer1.domain.GreetingConsumerMessage;
import com.microservice.demo.consumer1.repository.GreetingMessageRepository;
import com.microservice.demo.producer1.domain.GreetingProducerMessage;

@Component
public class GreetingMessageListener {
	
	public static final String TOPIC = "topic-demo";
	
	public static final String TOPIC_WITH_PARTITION = "topic-demo-with-partition";
	
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
//	@KafkaListener(topics = TOPIC, groupId = "${spring.kafka.consumer.group-id}")
//	@Transactional
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
	
	// SOLO RECIBE MENSAJES DEL TOPICO TOPIC_WITH_PARTITION Y PARTICIONES 0 Y 2
	@KafkaListener(topicPartitions = {
			@TopicPartition(topic = TOPIC_WITH_PARTITION, partitions = {"0", "2"})
	}, groupId = "${spring.kafka.consumer.group-id}")
	public void listenPartition0And2(@Payload GreetingProducerMessage producer, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) String partition) {
		System.out.println("Partition: " + partition);
		System.out.println(producer.toString());
	}
	
	@KafkaListener(topicPartitions = {
			@TopicPartition(topic = TOPIC_WITH_PARTITION, partitions = {"1"})
	}, groupId = "${spring.kafka.consumer.group-id}")
	public void listenPartition1A(ConsumerRecord<?, ?> record) {
		System.out.println("A");
		System.out.println(record.toString());
		System.out.println("Topic: "+record.topic());
		System.out.println("Partition: "+record.partition());
		System.out.println("Offset: "+record.offset());
		record.headers().forEach(header -> {System.out.println(header.key() + " - " + header.value());});
		
		Object key = record.key();
		Object value = record.value();
		System.out.println("Key: "+key);
		System.out.println("Value: "+value.toString());
		System.out.println("===============================================================================================");
	}
	
	@KafkaListener(topicPartitions = {
			@TopicPartition(topic = TOPIC_WITH_PARTITION, partitions = {"1"})
	}, groupId = "${spring.kafka.consumer.group-id}")
	public void listenPartition1B(ConsumerRecord<?, ?> record) {
		System.out.println("B");
		System.out.println(record.toString());
		System.out.println("Topic: "+record.topic());
		System.out.println("Partition: "+record.partition());
		System.out.println("Offset: "+record.offset());
		record.headers().forEach(header -> {System.out.println(header.key() + " - " + header.value());});
		
		Object key = record.key();
		Object value = record.value();
		System.out.println("Key: "+key);
		System.out.println("Value: "+value.toString());
		System.out.println("===============================================================================================");
	}
}
