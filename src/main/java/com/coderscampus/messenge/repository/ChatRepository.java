package com.coderscampus.messenge.repository;

import com.coderscampus.messenge.dto.Chat;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class ChatRepository {
    private Map<Long, List<Chat>> chats = new HashMap<>();

    public Optional<List<Chat>> findMessagesByChannel (Long channelId) {
        List<Chat> chatsByChannel = chats.get(channelId);
        return Optional.ofNullable(chatsByChannel);
    }

    public void saveMessagesByChannel(Long channelId, List<Chat> chatsByChannel) {
        chats.put(channelId, chatsByChannel);
    }
}

