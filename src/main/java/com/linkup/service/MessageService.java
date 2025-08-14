package com.linkup.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkup.dao.MessageRepository;
import com.linkup.model.ChatMessage;

@Service
public class MessageService {
    
    @Autowired
    private MessageRepository messageRepository;

    public ChatMessage saveMessage(ChatMessage message) {
        return messageRepository.save(message);
    }

    public List<ChatMessage> getMessagesBetween(String sender, String receiver) {
        return messageRepository.findBySenderAndReceiver(sender, receiver);
    }
}
