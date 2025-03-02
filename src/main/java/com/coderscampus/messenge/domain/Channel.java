package com.coderscampus.messenge.domain;

import java.util.List;

public class Channel {
    private Long id;
    private String name;
    private List <User> users;
    boolean isPrivate;

    public Channel(){

    }

    public Channel (Long id, String name, List <User> users, boolean isPrivate){
        this.id= id;
        this.name = name;
        this.users = users;
        this.isPrivate=isPrivate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
                "id=" + id +
                ", name='" + name + '\'' +
                ", users=" + users +
                ", isPrivate=" + isPrivate +
                '}';
    }
}
