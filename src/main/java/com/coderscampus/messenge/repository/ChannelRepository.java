package com.coderscampus.messenge.repository;

import com.coderscampus.messenge.dto.Channel;
import com.coderscampus.messenge.dto.Chat;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ChannelRepository {

    private final List<Channel> channelList = new ArrayList<>();

    private void createChannel(String name, int id) {
        Channel channel = new Channel();
        channel.setName(name);
        channel.setChannelId((long) id);
        channelList.add(channel);
    }

    @PostConstruct
    public void generateChannelList() {
        createChannel("Coder's Campus", 1);
        createChannel("Discord", 2);
        createChannel("Slack", 3);
        createChannel("Skype", 4);
    }

    public Channel findById(Long id) {
        return channelList.stream().filter(channel -> channel.getChannelId() == id)
                .findFirst().orElse(null);
    }

    public Channel save(Channel channel) {
        if (channel.getChannelId() == null) {
            // Check if channel with same name exists
            boolean channelExists = channelList.stream()
                    .anyMatch(c -> c.getName().equalsIgnoreCase(channel.getName()));
            
            if (channelExists) {
                throw new IllegalArgumentException("Channel with this name already exists");
            }
            
            channel.setChannelId((long) (channelList.size() + 1));
        }
        
        // If updating existing channel, remove old version
        channelList.removeIf(c -> c.getChannelId().equals(channel.getChannelId()));
        channelList.add(channel);
        return channel;
    }

    public Channel saveMessage(Channel channel, Chat chat) {
        Channel existingChannel = findById(channel.getChannelId());
        if (existingChannel != null) {
            existingChannel.getChats().add(chat);
            return existingChannel;
        }
        return channel;
    }

    public List<Channel> findAll() {
        return channelList;
    }
    
    public boolean delete(Long channelId) {
        return channelList.removeIf(channel -> channel.getChannelId().equals(channelId));
    }
}
