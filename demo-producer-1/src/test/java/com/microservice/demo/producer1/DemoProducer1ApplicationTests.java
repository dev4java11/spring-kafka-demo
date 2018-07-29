package com.microservice.demo.producer1;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.microservice.demo.producer1.domain.GreetingProducerMessage;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoProducer1ApplicationTests {
	
	@Autowired
	private KafkaTemplate<String, GreetingProducerMessage> kafkaTemplate;

	@Test
	public void contextLoads() {
		GreetingProducerMessage gm = GreetingProducerMessage.builder().uuid(UUID.randomUUID().toString()).description("Hello World").build();
		System.out.println(gm.toString());
	
		kafkaTemplate.send("topic-demo", gm)
			.addCallback(success -> {System.out.println(success);}, error -> {System.out.println(error);});
	}

}
