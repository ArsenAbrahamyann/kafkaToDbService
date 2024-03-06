package com.polixis.kafkaToDbService.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polixis.kafkaToDbService.api.MessageControllerApi;
import com.polixis.kafkaToDbService.exception.CustomJsonProcessingException;
import com.polixis.kafkaToDbService.model.Message;
import com.polixis.kafkaToDbService.service.MessageService;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/messages")
public class MessageController implements MessageControllerApi {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
    private static final String TOPIC_NAME = "json-to-db";

    private final KafkaTemplate<String, Message> kafkaTemplate;
    private final MessageService messageService;
    private final ObjectMapper objectMapper;

    public MessageController(KafkaTemplate<String, Message> kafkaTemplate, MessageService messageService, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.messageService = messageService;
        this.objectMapper = objectMapper;
    }

    @PostMapping
    public ResponseEntity<Object> sendMessageToKafka(@RequestBody @NotNull String message) {
        try {
            Message messageObject = objectMapper.readValue(message, Message.class);
            kafkaTemplate.send(TOPIC_NAME, messageObject);
            Link selfLink = WebMvcLinkBuilder.linkTo(MessageController.class)
                    .slash("api")
                    .slash("messages")
                    .withSelfRel();
            return ResponseEntity.ok()
                    .body(Map.of(
                            "message", "Message sent successfully to Kafka",
                            "_links", Map.of("self", selfLink)));
        } catch (JsonProcessingException e) {
            logger.error("Error processing JSON: {}, error message: {}", message, e.getMessage());
            throw new CustomJsonProcessingException("Error processing JSON message", e);
        }
    }

    @GetMapping
    public ResponseEntity<Object> getMessages() {
        try {
            List<Message> messages = messageService.getAllMessages();

            Link selfLink = WebMvcLinkBuilder.linkTo(MessageController.class)
                    .slash("api")
                    .slash("messages")
                    .withSelfRel();

            return ResponseEntity.ok()
                    .body(Map.of(
                            "messages", messages,
                            "_links", Map.of("self", selfLink)));
        } catch (Exception e) {
            logger.error("Failed to fetch messages from the database", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch messages from the database");
        }
    }

}
