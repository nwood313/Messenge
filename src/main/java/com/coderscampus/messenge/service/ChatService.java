package com.coderscampus.messenge.service;

import com.coderscampus.messenge.dto.Chat;
import com.coderscampus.messenge.repository.ChatRepository;
import com.coderscampus.messenge.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    private final UserRepository userRepo;
    private final ChatRepository chatRepo;

    public ChatService(UserRepository userRepo,
                       ChatRepository chatRepo) {
        this.userRepo = userRepo;
        this.chatRepo = chatRepo;
    }

    public Chat sendChats(Chat chat) {
        chatRepo.save(chat);
        return chat;
    }

    public List<Chat> getChats(Long channelId) {
        return chatRepo.findByChannelId(channelId);
    }
}
