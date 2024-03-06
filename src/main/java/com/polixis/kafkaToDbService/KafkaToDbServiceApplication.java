package com.polixis.kafkaToDbService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.polixis.kafkaToDbService"})
public class KafkaToDbServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaToDbServiceApplication.class, args);
	}

}
