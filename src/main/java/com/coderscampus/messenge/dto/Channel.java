package com.coderscampus.messenge.dto;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Channel {

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long channelId;

    private String name;

    //@ManyToMany(mappedBy = "channels")
    private List<User> users;

    //@OneToMany(mappedBy = "channel", cascade = CascadeType.ALL)
    private List<Chat> chats = new ArrayList<>();

    public Channel() {
    }

    public Channel(Long channelId, String name, List<User> users, List<Chat> chats) {
        this.channelId = channelId;
        this.name = name;
        this.users = users;
        this.chats = chats;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Chat> getChats() {
        return chats;
    }

    public void setChats(List<Chat> chats) {
        this.chats = chats;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "channelId=" + channelId +
                ", name='" + name + '\'' +
                ", users=" + users +
                '}';
    }
}
