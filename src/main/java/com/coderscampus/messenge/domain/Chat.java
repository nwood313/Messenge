package com.coderscampus.messenge.domain;

import java.time.LocalDateTime;

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
