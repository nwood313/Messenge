package com.coderscampus.messenge.service;

import com.coderscampus.messenge.dto.Channel;
import com.coderscampus.messenge.dto.Chat;
import com.coderscampus.messenge.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChannelService {

    private final ChannelRepository channelRepo;

    public ChannelService(ChannelRepository channelRepo) {
        this.channelRepo = channelRepo;
    }

    public Channel findById(Long id) {
        return channelRepo.findById(id);
    }

    public Channel save(Channel channel) {
        return channelRepo.save(channel);
    }

    public Channel save(Channel channel, Chat chat) {
        return channelRepo.saveMessage(channel, chat);
    }

    public List<Channel> findAll() {
        return channelRepo.findAll();
    }

    public List<Channel> getAllChannels() {
        return channelRepo.findAll();
    }

    public Channel createChannel(String name) {
        Channel channel = new Channel();
        channel.setName(name);
        return channelRepo.save(channel);
    }
}