package com.coderscampus.messenge.web;

import com.coderscampus.messenge.dto.Channel;
import com.coderscampus.messenge.dto.User;
import com.coderscampus.messenge.service.ChannelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller

public class ChannelController {

    private final ChannelService channelService;

    public ChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }

    @GetMapping("/channels")
    public String getChannels(@ModelAttribute("user") User user, ModelMap model) {
        List<Channel> channels = channelService.findAll();
        model.addAttribute("channels", channels);
        return "channels";
    }

    @GetMapping("/channels/{channelId}")
    public String getChannel(@PathVariable Long channelId, ModelMap model) {
        Channel channel = channelService.findById(channelId);
        if (channel == null) {
            return "redirect:/error";
        }
        List<Channel> channels = channelService.findAll();
        model.addAttribute("channels", channels);
        model.addAttribute("channel", channel);
        return "channel";
    }

}