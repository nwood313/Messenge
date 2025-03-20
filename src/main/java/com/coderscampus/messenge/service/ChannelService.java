package com.coderscampus.messenge.service;

import com.coderscampus.messenge.dto.Channel;
import com.coderscampus.messenge.dto.User;
import com.coderscampus.messenge.repository.ChannelRepository;
import com.coderscampus.messenge.repository.UserRepository;
import org.apache.catalina.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChannelService {
    private final UserRepository userRepository;
    private final ServerRepository serverRepository;
    private final ChannelRepository channelRepository;

    @Autowired
    public ChannelService(UserRepository userRepository,
                          ServerRepository serverRepository,
                          ChannelRepository channelRepository) {
        this.userRepository = userRepository;
        this.serverRepository = serverRepository;
        this.channelRepository = channelRepository;
    }

    public Channel createChannel(Server server, String name) {
        validateUserHasPermission(getCurrentUser(), server);

        Channel channel = new Channel();
        channel.setName(name);
        channel.setServer(server);

        return channelRepository.save(channel);
    }

    public Channel getChannel(Long channelId) {
        validateChannelExists(channelId);
        return channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelNotFoundException(channelId));
    }

    public List<Channel> getChannelsByServer(Long serverId) {
        Server server = serverRepository.findById(serverId)
                .orElseThrow(() -> new ServerNotFoundException(serverId));
        return channelRepository.findByServerId(serverId);
    }

    public Channel updateChannel(Long channelId, String name) {
        Channel channel = getChannel(channelId);
        channel.setName(name);
        return channelRepository.save(channel);
    }

    public void deleteChannel(Long channelId) {
        validateChannelExists(channelId);
        channelRepository.deleteById(channelId);
    }

    private void validateChannelExists(Long channelId) {
        if (!channelRepository.existsById(channelId)) {
            throw new ChannelNotFoundException(channelId);
        }
    }

    private void validateUserHasPermission(User user, Server server) {
        if (!server.getOwner().getId().equals(user.getId())) {
            throw new UnauthorizedException("User does not have permission to manage this server");
        }
    }

    private User getCurrentUser() {
        return userRepository.findByUsername(SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}