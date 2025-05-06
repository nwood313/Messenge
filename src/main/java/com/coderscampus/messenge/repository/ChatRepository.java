package com.coderscampus.messenge.repository;

import com.coderscampus.messenge.dto.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ChatRepository {
    private final List<Chat> chats = new ArrayList<>();

    public void save(Chat chat) {
        chats.add(chat);
    }

    public List<Chat> findAll(){
        return chats;
    }
}

