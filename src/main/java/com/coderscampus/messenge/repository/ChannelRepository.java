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
        createChannel("Teams", 4);
    }

    public Channel findById(Long id) {
        return channelList.stream().filter(channel -> channel.getChannelId() == id)
                .findFirst().orElse(null);
    }

    public Channel saveMessage(Channel channel, Chat chat) {
        findById(channel.getChannelId()).getChats().add(chat);
        return channel;
    }

    public List<Channel> findAll() {
        return channelList;
    }
}
