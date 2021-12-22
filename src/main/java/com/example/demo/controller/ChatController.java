package com.example.demo.controller;

import com.example.demo.entiy.Greeting;
import com.example.demo.entiy.Message;
import com.example.demo.entiy.User;
import com.example.demo.repos.MessageRepo;
import com.example.demo.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.HtmlUtils;

import java.time.OffsetDateTime;
import java.util.List;

@Controller
public class ChatController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MessageRepo messageRepo;
    @GetMapping("/chat")
    public String getChat(@AuthenticationPrincipal User user, Model model){
        User userBD = userRepo.findById(user.getId()).orElse(new User());
        List<Message> messageList=messageRepo.findAll();
        model.addAttribute("messages",messageList);
        model.addAttribute("rating",userBD.getRating());
        model.addAttribute("username",userBD.getUsername());
        return "chat";
    }
    @MessageMapping("/webs")
    @SendTo("/topic/webs")
    public Greeting greeting(Message message) throws Exception {
        message.setAuthor((User) userRepo.findByUsername(message.getAuthor().getUsername()));
        messageRepo.save(message);
        Thread.sleep(1000);
        return new Greeting(message.getAuthor().getUsername()+": "+ HtmlUtils.htmlEscape(message.getContent()));
    }

}

