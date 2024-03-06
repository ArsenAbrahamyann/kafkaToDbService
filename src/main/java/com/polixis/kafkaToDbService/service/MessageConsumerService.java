package com.polixis.kafkaToDbService.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polixis.kafkaToDbService.model.Message;
import com.polixis.kafkaToDbService.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(MessageConsumerService.class);
    private final MessageRepository repository;

    public MessageConsumerService(MessageRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "json-to-db", groupId = "consumer-group")
    public void consume(Message message) {
        logger.info("Received message from Kafka content: {}, id: {}", message.getContent(), message.getId());
        repository.save(message);
    }
}