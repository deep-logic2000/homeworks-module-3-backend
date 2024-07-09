package com.example.homework_module3.Homework05.Controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/accountStatus")
    @SendTo("/topic/accountStatus")
    public String sendStatusUpdate(String message) {
        return message;
    }
}
