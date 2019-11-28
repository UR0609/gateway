package com.example.websocket.controller;

import com.example.websocket.config.BulletMessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BulletController {

    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/chat")
    @SendTo("/toAll/bulletScreen")             //SendTo 发送至 Broker 下的指定订阅路径
    public String say(BulletMessageVO clientMessage) {
        String result=null;
        if (clientMessage!=null){
            result=clientMessage.getUsername()+":"+clientMessage.getMessage();
        }
        return result;
    }

    @GetMapping("/bullet/websocket")
    public String msgReply(@RequestParam String msg) {
        template.convertAndSend("/toAll/bulletScreen", msg);
        return msg;
    }

}
