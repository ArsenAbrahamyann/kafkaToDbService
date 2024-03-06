package com.polixis.kafkaToDbService.api;

import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;

public interface MessageControllerApi {
    ResponseEntity<Object> sendMessageToKafka(@NotNull String message);

    ResponseEntity<Object> getMessages();
}
