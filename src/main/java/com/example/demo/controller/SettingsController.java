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

import java.util.ArrayList;
import java.util.List;

@Controller
public class SettingsController {
    @Autowired
    AiRepo aiRepo;
    @Autowired
    private UserRepo userRepo;
    @GetMapping("/settings")
    public String settings(Model model){
        List<AI> aiList = aiRepo.findAll();
        model.addAttribute("aiList",aiList);
        model.addAttribute("save_stat",0);
        return "settings";
    }
    @PostMapping ("/settings")
    public String post(@AuthenticationPrincipal User user, @RequestParam String nameAi, @RequestParam int difficulty, Model model){
        List<AI> aiList = aiRepo.findAll();
        model.addAttribute("aiList",aiList);
        model.addAttribute("save_stat",1);
        System.out.println(nameAi+" "+difficulty);
        User userSet=userRepo.findById(user.getId()).orElse(new User());
        userSet.setNameAi(nameAi);
        userSet.setDifficultAi(difficulty);
        userRepo.save(userSet);
        return "settings";
    }
}
