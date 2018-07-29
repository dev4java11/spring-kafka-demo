package com.microservice.demo.producer1.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class GreetingProducerMessage {

	private String uuid;
	private String description;
}
