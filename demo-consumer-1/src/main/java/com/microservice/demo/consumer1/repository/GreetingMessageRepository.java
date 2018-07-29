package com.microservice.demo.consumer1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.demo.consumer1.domain.GreetingConsumerMessage;

public interface GreetingMessageRepository extends JpaRepository<GreetingConsumerMessage, String> {

}
