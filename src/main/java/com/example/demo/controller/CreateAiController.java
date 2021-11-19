package com.example.demo.controller;

import com.example.demo.entiy.AI;
import com.example.demo.entiy.User;
import com.example.demo.repos.AiRepo;
import com.example.demo.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CreateAiController {
    @Autowired
    AiRepo aiRepo;
    @Autowired
    UserRepo userRepo;
    @GetMapping("/createAi")
    public String createAiGet(Model model,@AuthenticationPrincipal User user){
        User userBD = userRepo.findById(user.getId()).orElse(new User());
        model.addAttribute("win",userBD.getWin());
        model.addAttribute("def",userBD.getDef());
        model.addAttribute("username",userBD.getUsername());
        model.addAttribute("save_stat",0);
        return "createAi";
    }
    @PostMapping("/createAi")
    public String post(@RequestParam String nameAi, Model model){
        AI ai=aiRepo.findByNameAi(nameAi);
        if(ai!=null){
            model.addAttribute("report",404);
            model.addAttribute("save_stat",1);
            return "createAi";
        }
        ai=new AI();
        ai.setNameAi(nameAi);
        aiRepo.save(ai);
        model.addAttribute("report",0);
        return "createAi";
    }
}
