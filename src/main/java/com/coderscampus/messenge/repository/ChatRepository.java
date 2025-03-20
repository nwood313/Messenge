package com.coderscampus.messenge.repository;

import com.coderscampus.messenge.dto.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByChannelId(Long channelId);
}

