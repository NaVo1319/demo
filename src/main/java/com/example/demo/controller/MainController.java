package com.example.demo.controller;

import com.example.demo.Service.ActiveUserService;
import com.example.demo.entiy.User;
import com.example.demo.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MainController {
    @Autowired
    private UserRepo userRepo;
    ActiveUserService activeUserService=new ActiveUserService();
    @GetMapping("/")
    public String Homepage(Model model, @AuthenticationPrincipal User user){
        User userBD = userRepo.findById(user.getId()).orElse(new User());
        model.addAttribute("rating",userBD.getRating());
        model.addAttribute("username",userBD.getUsername());
        return "index";
    }
}
