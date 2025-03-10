package com.coderscampus.messenge.web;


import org.springframework.stereotype.Controller;


import com.coderscampus.assignment14.domain.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChannelController {

    @MessageMapping("/channel.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage (@Payload ChatMessage message){
        return message;
    }

    @MessageMapping("/channel.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage message, SimpMessageHeaderAccessor headerAccessor){
        headerAccessor.getSessionAttributes().put("username", message.getSender());
        return message;
    }
}