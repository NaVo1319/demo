package com.example.demo.controller;

import com.example.demo.entiy.Greeting;
import com.example.demo.entiy.Message;
import com.example.demo.entiy.MessageBD;
import com.example.demo.entiy.User;
import com.example.demo.repos.MessageRepo;
import com.example.demo.repos.UserRepo;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Controller
public class ChatController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MessageRepo messageRepo;
    User user;
    @GetMapping("/chat")
    public String getChat(@AuthenticationPrincipal User user, Model model){
        User userBD = userRepo.findById(user.getId()).orElse(new User());
        List<MessageBD> messageBDList=messageRepo.findAll();
        model.addAttribute("messages",messageBDList);
        model.addAttribute("win",userBD.getWin());
        model.addAttribute("def",userBD.getDef());
        model.addAttribute("username",userBD.getUsername());
        this.user=user;
        return "/chat/chat";
    }
    @MessageMapping("/webs")
    @SendTo("/topic/webs")
    public Greeting greeting(Message message) throws Exception {
        MessageBD mbd=new MessageBD(message.getName(), user.getUsername());
        messageRepo.save(mbd);
        Thread.sleep(1000);
        return new Greeting(user.getUsername()+": "+ HtmlUtils.htmlEscape(message.getName()));
    }

}

