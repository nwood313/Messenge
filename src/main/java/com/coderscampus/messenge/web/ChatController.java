package com.coderscampus.messenge.web;

import com.coderscampus.messenge.dto.Channel;
import com.coderscampus.messenge.service.ChannelService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.coderscampus.messenge.dto.Chat;



@Controller
public class ChatController {

    private final ChannelService channelService;

    public ChatController(ChannelService channelService) {
        this.channelService = channelService;
    }

    @PostMapping("/channels/{channelId}/messages")
    @ResponseBody
    public boolean saveMessage(@PathVariable Long channelId, @RequestBody Chat chat) {
        try {
            Channel channel = channelService.findById(channelId);
            channelService.save(channel, chat);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @ResponseBody
    @GetMapping("/channels/{channelId}/get-messages")
    public List<Channel> getChats(@PathVariable Long channelId) {
        Channel channel = channelService.findById(channelId);
        return channelService.findAll();
    }
}