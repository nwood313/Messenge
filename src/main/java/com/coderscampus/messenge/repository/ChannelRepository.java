package com.coderscampus.messenge.repository;

import com.coderscampus.messenge.dto.Channel;
import com.coderscampus.messenge.dto.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChannelRepository extends JpaRepository <Channel, Long> {
    Chat findByUsername(String username);
    List<Chat> findByChannelId(Long channelId);

}
