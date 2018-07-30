package com.microservice.demo.producer1.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.demo.producer1.domain.GreetingProducerMessage;

@RestController
@RequestMapping("/greeting")
public class GreetingController {
	
	public static final String TOPIC = "topic-demo";
	
	public static final String TOPIC_WITH_PARTITION = "topic-demo-with-partition";

	private KafkaTemplate<String, GreetingProducerMessage> template;
	
	@Autowired
	public void setTemplate(KafkaTemplate<String, GreetingProducerMessage> template) {
		this.template = template;
	}
	
	// MANDA UN MENSAJE AL TOPICO
	@GetMapping("/{message}")
	public String createMessage(@PathVariable String message) {
		GreetingProducerMessage gm = GreetingProducerMessage.builder().uuid(UUID.randomUUID().toString()).description(message).build();
		template.send(TOPIC, gm);
		return "Mensaje entregado exitosamente.";
	}
	
	// MANDA EL MENSAJE A UNA PARTICION DE UN TOPICO
	@GetMapping("/{partition}/{message}")
	public String createMessage(@PathVariable Integer partition, @PathVariable String message) {
		GreetingProducerMessage gm = GreetingProducerMessage.builder().uuid(UUID.randomUUID().toString()).description(message).build();
		template.send(TOPIC_WITH_PARTITION, partition, gm.getUuid(), gm);
		return "Mensaje entregado exitosamente.";
	}
}
