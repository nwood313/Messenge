package com.coderscampus.messenge.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;


@Entity
public class Chat {
    private String text;
    private LocalDateTime momentInTime;
    private Long channelId;

    public Chat(){

    }
    public Chat(String text, LocalDateTime momentInTime, Long channelId) {
        this.text= text;
        this.momentInTime = momentInTime;
        this.channelId= channelId;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public LocalDateTime getMomentInTime() {
        return momentInTime;
    }
    public void setMomentInTime(LocalDateTime momentInTime) {
        this.momentInTime = momentInTime;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getChannelId() {
        return channelId;
    }
    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }
    @Override
    public String toString() {
        return "Chat{" +
                "text='" + text + '\'' +
                ", momentIntime=" + momentInTime +
                ", channelId=" + channelId +
                '}';
    }
}
