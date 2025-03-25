package com.coderscampus.messenge.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.coderscampus.messenge.dto.Chat;
import com.coderscampus.messenge.service.ChatService;

@RestController
public class ChatController {
    @Autowired
    private ChatService chatService;

    @GetMapping("/chats/{channelId}")
    public List<Chat> getChatsByChannelId(@PathVariable Long channelId) {
        List<Chat> channelChats = chatService.getChats(channelId);
        return channelChats;
    }

    @PostMapping("/chats")
    public Chat postChatsToUserId(@RequestBody Chat chat) {
        return chatService.sendChats(chat);
    }
}