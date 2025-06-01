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
        initializeDefaultChannels();
    }

    private void initializeDefaultChannels() {
        List<Channel> existingChannels = channelRepo.findAll();
        if (existingChannels.isEmpty()) {
            createChannel("Coder's Campus");
            createChannel("Discord");
            createChannel("Slack");
            createChannel("Skype");
        }
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
    
    public boolean deleteChannel(Long channelId) {
        // Don't allow deleting default channels
        Channel channel = findById(channelId);
        if (channel == null) {
            return false;
        }
        
        String channelName = channel.getName().toLowerCase();
        if (channelName.equals("general") || channelName.equals("random") || 
            channelName.equals("help") || channelName.equals("announcements")) {
            return false;
        }
        
        channelRepo.delete(channelId);
        return true;
    }
}