package com.example.demo.controller;

import com.example.demo.entiy.*;
import com.example.demo.repos.AiRepo;
import com.example.demo.repos.TableRepo;
import com.example.demo.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
@Controller
public class SingleController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    AiRepo aiRepo;
    @Autowired
    TableRepo tableRepo;
    @GetMapping("/singleplayer")
    public String regPage(@AuthenticationPrincipal User user, Model model){
        GameTable gameTable=tableRepo.findByUserId(user.getId());
        if(gameTable!=null){
            tableRepo.deleteById(gameTable.getId());
        }
        gameTable=new GameTable();
        gameTable.setUser(userRepo.findById(user.getId()).orElse(new User()));
        tableRepo.save(gameTable);
        model.addAttribute("rating",gameTable.getUser().getRating());
        model.addAttribute("username",gameTable.getUser().getUsername());
        return "SingleMenu";
    }
    @RequestMapping(value = "/singleplayer", method = RequestMethod.POST)
    @ResponseBody
    public String Game(@RequestBody String r,  Model model,@AuthenticationPrincipal User user) {
        GameTable gameTable=tableRepo.findByUserId(user.getId());
        AI ai= user.getSelectedAi();
        int difficult= user.getDifficultAi();
        if(difficult!=60 || difficult!=30 || difficult!=10){
            difficult=60;
        }
        if(ai==null){
            ai=aiRepo.findByNameAi(user.getSelectedAi().getNameAi());
            System.out.println(ai.getList());
        }
        int t=Integer.parseInt(r);
        int x=t/10-1;
        int y=t%10-1;
        gameTable.set(x*3+y,'1');
        if(gameTable.isWin('1')){
            gameTable.restart();
            gameTable.getUser().rating(true);
            userRepo.save(gameTable.getUser());
            tableRepo.deleteById(gameTable.getId());
            return "20";
        }
        if(gameTable.pat()){
            gameTable.restart();
            tableRepo.deleteById(gameTable.getId());
            return "40";
        }
        gameTable=ai.makeStep(gameTable,difficult);
        if(gameTable.isWin('2')){
            gameTable.restart();
            gameTable.getUser().rating(false);
            userRepo.save(gameTable.getUser());
            tableRepo.deleteById(gameTable.getId());
            return "30";
        }
        aiRepo.save(ai);
        tableRepo.save(gameTable);
        return ai.getLastStep()+"";
    }
}
