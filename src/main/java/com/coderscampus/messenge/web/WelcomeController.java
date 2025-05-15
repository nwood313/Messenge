package com.coderscampus.messenge.web;

import com.coderscampus.messenge.service.ChannelService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

    private final ChannelService channelService;

    public WelcomeController(ChannelService channelService) {
        this.channelService = channelService;
    }

    @GetMapping("/terms")
    public String getTerms() {
        return "terms";
    }
} 