package com.linkup.controller;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.linkup.dao.UserDAO;
import com.linkup.model.ChatMessage;
import com.linkup.model.User;
import com.linkup.service.MessageService;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private MessageService messageService;

    private final UserDAO userDAO = new UserDAO();

    @MessageMapping("/private-message")
    public void receivePrivateMessage(@Payload ChatMessage message) {
        message.setTimestamp(OffsetDateTime.now());
        messageService.saveMessage(message);
//
//        simpMessagingTemplate.convertAndSendToUser(
//            message.getReceiver(), 
//            "/queue/messages", 
//            message
//        );
        
        simpMessagingTemplate.convertAndSend(
        	    "/user/" + message.getReceiver() + "/queue/messages",
        	    message
        	);

    }
    
    


    @GetMapping("/api/chat/users")
    public List<User> getAllUsers() {
        return userDAO.findAll(); 
    }
}
