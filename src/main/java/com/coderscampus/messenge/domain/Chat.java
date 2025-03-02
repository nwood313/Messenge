package com.coderscampus.messenge.domain;

import java.time.LocalDateTime;

public class Chat {
    private String text;
    private LocalDateTime momentIntime;
    private Long channelId;

    public Chat(){

    }

    public Chat(String text, LocalDateTime momentIntime, Long channelId) {
        this.text= text;
        this.momentIntime= momentIntime;
        this.channelId= channelId;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getMomentIntime() {
        return momentIntime;
    }

    public void setMomentIntime(LocalDateTime momentIntime) {
        this.momentIntime = momentIntime;
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
                ", momentIntime=" + momentIntime +
                ", channelId=" + channelId +
                '}';
    }
}
