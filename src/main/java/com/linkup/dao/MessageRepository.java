package com.linkup.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.linkup.model.ChatMessage;

@Repository
public interface MessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByReceiver(String receiver);
    List<ChatMessage> findBySenderAndReceiver(String sender, String receiver);
}
