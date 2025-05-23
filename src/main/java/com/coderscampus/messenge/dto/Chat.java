package com.coderscampus.messenge.dto;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
public class Chat {
    private Long messageId;
    private String text;
    private OffsetDateTime momentInTime;
    private Long channelId;
    private User sender;

    public Chat(){

    }
    public Chat(String text, OffsetDateTime momentInTime, Long channelId) {
        this.text= text;
        this.momentInTime = momentInTime;
        this.channelId= channelId;
    }

    public Chat(String text, OffsetDateTime momentInTime, Long channelId, User sender) {
        this.text = text;
        this.momentInTime = momentInTime;
        this.channelId = channelId;
        this.sender = sender;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public OffsetDateTime getMomentInTime() {
        return momentInTime;
    }
    public void setMomentInTime(OffsetDateTime momentInTime) {
        this.momentInTime = momentInTime;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getMessageId() {
        return messageId;
    }
    
    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }
    
    public Long getChannelId() {
        return channelId;
    }
    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "messageId=" + messageId +
                ", text='" + text + '\'' +
                ", momentInTime=" + momentInTime +
                ", channelId=" + channelId +
                ", sender=" + (sender != null ? sender.getUsername() : "null") +
                '}';
    }
}
