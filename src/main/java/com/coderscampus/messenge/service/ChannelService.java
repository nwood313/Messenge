package com.coderscampus.messenge.service;

import com.coderscampus.messenge.dto.Channel;
import com.coderscampus.messenge.dto.User;
import com.coderscampus.messenge.repository.ChannelRepository;
import com.coderscampus.messenge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChannelService {
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;

    @Autowired
    public ChannelService(UserRepository userRepository,
                          ChannelRepository channelRepository) {
        this.userRepository = userRepository;
        this.channelRepository = channelRepository;
    }

    public Channel createChannel(String name,Long channelId) {
        Channel channel = new Channel();
        channel.setName(name);
        return channelRepository.save(channel);
    }

    public Optional<Channel> getChannel(Long channelId) {
        return Optional.of(channelRepository.findById(channelId).orElse(new Channel()));

    }

    public Channel updateChannel(Long channelId, String name) {
        Channel channel = channelRepository.findById(channelId)
                .orElse(null);

        if (channel == null) {
            return createChannel(name,channelId);
        }

        channel.setName(name);
        return channelRepository.save(channel);
    }

    public void deleteChannel(Long channelId) {
        channelRepository.deleteById(channelId);
    }


    @Autowired
    private ChannelRepository channelRepo;

    public Channel findChannelById(Long channelId) {
        return channelRepo.findById(channelId).orElse(new Channel());
    }

    public List<Channel> findAll() {
        return channelRepo.findAll();
    }


// future feature
//    private void validateUserHasPermission(User user, Server server) {
//        if (!server.getOwner().getId().equals(user.getId())) {
//            throw new UnauthorizedException("User does not have permission to manage this server");
//        }
//    }

}