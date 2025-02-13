package com.eurotorg.test_logs.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketController {

    @MessageMapping("/logs")
    @SendTo("/topic/logs")
    public String handleLogMessage(String message) {
        // Handle incoming messages (if needed)
        return message;
    }
}
