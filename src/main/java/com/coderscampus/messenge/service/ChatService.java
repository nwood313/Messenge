package com.coderscampus.messenge.service;

import com.coderscampus.messenge.dto.Chat;
import com.coderscampus.messenge.repository.ChatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public void save(Chat chat) {
        chatRepository.save(chat);
    }

    public List<Chat> findAll(){
        return chatRepository.findAll();
    }
}
