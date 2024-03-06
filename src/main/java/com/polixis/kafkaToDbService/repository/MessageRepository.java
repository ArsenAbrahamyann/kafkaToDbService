package com.polixis.kafkaToDbService.repository;

import com.polixis.kafkaToDbService.model.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
}