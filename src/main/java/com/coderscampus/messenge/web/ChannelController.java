package com.coderscampus.messenge.web;


import com.coderscampus.messenge.dto.Channel;
import com.coderscampus.messenge.service.ChannelService;
import com.coderscampus.messenge.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import com.coderscampus.messenge.dto.Chat;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
public class ChannelController {

    @Autowired
    private ChannelService channelService;
    @Autowired private ChatService chatService;

    @GetMapping("/")
    public String welcomeRedirect(){
        return "redirect:/welcome";
    }
    @GetMapping ("/channels/{channelId}")
    public String getChannel(ModelMap model, @PathVariable Long channelId) {
        Optional<Channel> channel = channelService.getChannel(channelId);
        List<Chat> chatsByChannel =
                chatService.getChats(channelId);
                model.put("chat", chatsByChannel);
                model.put("channel", channel);

                return "channel";
    }
    @GetMapping("/welcome")
    public String getWelcome(ModelMap model){
        List<Channel> channels = channelService.findAll();
        model.put("channels", channels);

        return "welcome";
    }


}


//Websockets Research
//@Controller
//public class ChannelController {
//
//    @MessageMapping("/channel.sendMessage")
//    @SendTo("/topic/public")
//    public ChatMessage sendMessage (@Payload ChatMessage message){
//        return message;
//    }
//
//    @MessageMapping("/channel.addUser")
//    @SendTo("/topic/public")
//    public ChatMessage addUser(@Payload ChatMessage message, SimpMessageHeaderAccessor headerAccessor){
//        headerAccessor.getSessionAttributes().put("username", message.getSender());
//        return message;
//    }
//}