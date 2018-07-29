package com.microservice.demo.producer1.domain;

public class GreetingProducerMessage {

	private String uuid;
	private String description;
	
	public GreetingProducerMessage() {
		
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
