package com.coderscampus.messenge.repository;

import com.coderscampus.messenge.dto.Channel;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ChannelRepository {
    private List<Channel> channels = new ArrayList<>();

    public ChannelRepository() {
        Channel general = new Channel();
        general.setChannelId(1L);
        general.setName("Messenge");

        channels.add(general);
    }

    public Optional<Channel> findById(Long channelId) {
        return channels.stream()
                .filter(channel -> channel.getChannelId().equals(channelId))
                .findAny();
    }

    public List<Channel> findAll() {
        return channels;
    }

    public Channel save(Channel channel) {
     return channel;
    }

    public void deleteById(Long channelId) {
        deleteById(channelId);
    }
}
