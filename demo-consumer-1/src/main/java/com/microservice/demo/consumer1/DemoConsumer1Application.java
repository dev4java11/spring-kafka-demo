package com.microservice.demo.consumer1;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.listener.ContainerStoppingErrorHandler;
import org.springframework.kafka.listener.ErrorHandler;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;

@SpringBootApplication
public class DemoConsumer1Application {

	public static void main(String[] args) {
		SpringApplication.run(DemoConsumer1Application.class, args);
	}
	
//	configuracion del aplicativo
//	server.port 8081
//	spring.application.name consumer_a
//	spring.datasource.url jdbc:mysql://localhost:3306/a
//	spring.kafka.consumer.group-id greeting_a
	
	@Configuration
	public class KafkaConfig implements InitializingBean{
		
		private ConcurrentKafkaListenerContainerFactory<?, ?> factory;
		
		@Autowired
		public void setFactory(ConcurrentKafkaListenerContainerFactory<?, ?> factory) {
			this.factory = factory;
		}
		
		
		@Bean
		/**
		 * Este bean permite reintentar consumir el mensaje en caso
		 * de que se produzca una excepcion cuando se consume el mensaje
		 * @return
		 */
		public ErrorHandler seekToCurrentErrorHandler() {
			return new SeekToCurrentErrorHandler();
		}
		
		@Bean
		/**
		 * Este bean permite que no se vuelva a consumir el mensaje
		 * dado que el listener lanzo una excepcion
		 * @return
		 */
		public ErrorHandler containerStoppingErrorHandler() {
			return new ContainerStoppingErrorHandler();
		}
		
		@Override
		public void afterPropertiesSet() throws Exception {
			factory.getContainerProperties().setErrorHandler(containerStoppingErrorHandler());
		}
	}
}
