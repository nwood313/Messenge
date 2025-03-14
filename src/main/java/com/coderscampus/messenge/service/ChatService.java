package com.coderscampus.messenge.service;

package com.coderscampus.messenge.service;

import com.coderscampus.messenge.dto.Chat;
import com.coderscampus.messenge.repository.ChatRepository;
import com.coderscampus.messenge.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;

    public ChatService(UserRepository userRepo,
                       ChatRepository chatRepo) {
        this.userRepository = userRepo;
        this.chatRepository = chatRepo;
    }

    public void sendChats(Chat chat) {
        chatRepo.save(chat);
    }

    public List<Chat> getChats(Long channelId) {
        return chatRepo.findByChannelId(channelId);
    }
}
