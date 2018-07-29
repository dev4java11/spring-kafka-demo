package com.microservice.demo.consumer1.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.ToString;

@Entity
@Table(name = "message")
@Builder
@ToString
public class GreetingConsumerMessage {
	
	@Id
	@Column(name = "uuid", length = 100)
	private String uuid;
	@Column(name = "description", length = 500)
	private String description;
	
	public GreetingConsumerMessage() {
		
	}
	
	public GreetingConsumerMessage(String uuid, String description) {
		super();
		this.uuid = uuid;
		this.description = description;
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
