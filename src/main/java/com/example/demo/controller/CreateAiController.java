package com.example.demo.controller;

import com.example.demo.entiy.AI;
import com.example.demo.entiy.User;
import com.example.demo.repos.AiRepo;
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
    @GetMapping("/createAi")
    public String createAiGet(Model model){
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
