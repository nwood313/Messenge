package com.coderscampus.messenge.repository;

import com.coderscampus.messenge.dto.Channel;
import com.coderscampus.messenge.dto.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChannelRepository extends JpaRepository <Channel, Long> {
    Chat findByUsername(String username);
    List<Chat> findByChannelId(Long channelId);

}
