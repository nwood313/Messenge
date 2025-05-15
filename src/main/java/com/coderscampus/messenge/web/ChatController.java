package com.coderscampus.messenge.web;

import com.coderscampus.messenge.dto.Channel;
import com.coderscampus.messenge.dto.Chat;
import com.coderscampus.messenge.dto.User;
import com.coderscampus.messenge.service.ChannelService;
import com.coderscampus.messenge.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ChatController {

    private final ChannelService channelService;
    
    public ChatController(ChannelService channelService) {
        this.channelService = channelService;
    }

    @PostMapping("/channels/{channelId}/messages")
    @ResponseBody
    public ResponseEntity<?> saveMessage(@PathVariable Long channelId, @RequestBody Chat chat, HttpSession session) {
        try {
            // Get the user from the session
            User user = (User) session.getAttribute("user");
            if (user == null) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "User not authenticated");
                return ResponseEntity.badRequest().body(response);
            }
            
            // Set the sender
            chat.setSender(user);
            
            Channel channel = channelService.findById(channelId);
            if (channel == null) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Channel not found");
                return ResponseEntity.notFound().build();
            }
            
            channelService.save(channel, chat);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Message saved successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @ResponseBody
    @GetMapping("/channels/{channelId}/get-messages")
    public List<Channel> getChats(@PathVariable Long channelId) {
        Channel channel = channelService.findById(channelId);
        return channelService.findAll();
    }
}