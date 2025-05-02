package com.coderscampus.messenge.service;

import com.coderscampus.messenge.dto.Channel;
import com.coderscampus.messenge.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChannelService {

    @Autowired
    ChannelRepository channelRepository;


    public ChannelService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    public Channel createChannel(String name,Long channelId) {
        Channel channel = new Channel();
        channel.setName(name);
        channel.setChannelId(channelId);
        return channelRepository.save(channel);
    }

    public Optional<Channel> getChannel(Long channelId) {
        return channelRepository.findById(channelId);
    }

    public Optional<Channel> updateChannel(Long channelId, String name) {
        return channelRepository.findById(channelId)
                .map(channel -> {
                    channel.setName(name);
                    return channelRepository.save(channel);
                });
    }

    public void deleteChannel(Long channelId) {
        channelRepository.deleteById(channelId);
    }

    public List<Channel> findAll() {
        return channelRepository.findAll();
    }
}