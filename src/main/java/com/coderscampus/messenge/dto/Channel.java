package com.coderscampus.messenge.dto;

import java.util.List;

public class Channel {
    private Long channelId;
    private String name;
    private List <User> users;
    boolean isPrivate;

    public Channel(){

    }

    public Channel (Long channelId, String name, List <User> users, boolean isPrivate){
        this.channelId = channelId;
        this.name = name;
        this.users = users;
        this.isPrivate = isPrivate;
    }

    public Long getchannelId() {
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

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "channelId=" + channelId +
                ", name='" + name + '\'' +
                ", users=" + users +
                ", isPrivate=" + isPrivate +
                '}';
    }
}
