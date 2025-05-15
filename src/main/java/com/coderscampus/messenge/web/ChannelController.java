package com.coderscampus.messenge.web;

import com.coderscampus.messenge.dto.Channel;
import com.coderscampus.messenge.dto.User;
import com.coderscampus.messenge.service.ChannelService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ChannelController {

    private final ChannelService channelService;

    public ChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }

    @GetMapping("/channels")
    public String getChannels(HttpSession session, ModelMap model) {
        // Get user from session and add to model
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/welcome";
        }
        model.addAttribute("user", user);
        
        List<Channel> channels = channelService.getAllChannels();
        
        // Initialize default channels if none exist
        if (channels.isEmpty()) {
            channels.add(channelService.createChannel("general"));
            channels.add(channelService.createChannel("random"));
            channels.add(channelService.createChannel("help"));
        }
        
        model.addAttribute("channels", channels);
        if (!channels.isEmpty()) {
            model.addAttribute("currentChannel", channels.get(0));
        }
        return "channel";
    }

    @GetMapping("/channels/{channelId}")
    public String getChannel(@PathVariable Long channelId, HttpSession session, ModelMap model) {
        // Get user from session and add to model
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/welcome";
        }
        model.addAttribute("user", user);
        
        Channel channel = channelService.findById(channelId);
        if (channel == null) {
            return "redirect:/channels";
        }
        List<Channel> channels = channelService.findAll();
        model.addAttribute("channels", channels);
        model.addAttribute("currentChannel", channel);
        return "channel";
    }

    @PostMapping("/channels/create")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> createChannel(@RequestBody Map<String, String> payload) {
        String name = payload.get("name");
        if (name == null || name.trim().isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", "Channel name cannot be empty");
            return ResponseEntity.badRequest().body(response);
        }

        Channel channel = new Channel();
        channel.setName(name.trim());
        channel = channelService.save(channel);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("channelId", channel.getChannelId());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/channels/{channelId}/rename")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> renameChannel(@PathVariable Long channelId, @RequestBody Map<String, String> payload) {
        String newName = payload.get("name");
        if (newName == null || newName.trim().isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", "Channel name cannot be empty");
            return ResponseEntity.badRequest().body(response);
        }

        Channel channel = channelService.findById(channelId);
        if (channel != null) {
            channel.setName(newName.trim());
            channelService.save(channel);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/channels/{channelId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> deleteChannel(@PathVariable Long channelId) {
        Channel channel = channelService.findById(channelId);
        if (channel == null) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", "Channel not found");
            return ResponseEntity.notFound().build();
        }

        // Try to delete the channel
        try {
            boolean deleted = channelService.deleteChannel(channelId);
            Map<String, Object> response = new HashMap<>();
            if (deleted) {
                response.put("success", true);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Cannot delete default channels");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", "Failed to delete channel: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}