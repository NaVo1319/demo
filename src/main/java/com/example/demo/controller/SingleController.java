package com.example.demo.controller;

import com.example.demo.entiy.*;
import com.example.demo.repos.AiRepo;
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
    private GameTable gameTable=new GameTable();
    private AI ai=null;
    @GetMapping("/singleplayer")
    public String regPage(@AuthenticationPrincipal User user, Model model){
        if(ai==null){
            ai=aiRepo.findByNameAi("BrainAi");
            System.out.println(ai.getList());
        }
        gameTable.setUser(userRepo.findById(user.getId()).orElse(new User()));
        model.addAttribute("win",gameTable.getUser().getWin());
        model.addAttribute("def",gameTable.getUser().getDef());
        model.addAttribute("username",gameTable.getUser().getUsername());
        return "SingleMenu";
    }
    @RequestMapping(value = "/singleplayer", method = RequestMethod.POST)
    @ResponseBody
    public String Game(@RequestBody String r,  Model model,@AuthenticationPrincipal User user) {
        int difficult= user.getDifficultAi();
        if(difficult!=60 || difficult!=30 || difficult!=10){
            difficult=60;
        }
        if(ai==null){
            ai=aiRepo.findByNameAi(user.getNameAi());
            System.out.println(ai.getList());
        }
        int t=Integer.parseInt(r);
        int x=t/10-1;
        int y=t%10-1;
        gameTable.set(x*3+y,'1');
        if(gameTable.isWin('1')){
            gameTable.restart();
            gameTable.getUser().win();
            userRepo.save(gameTable.getUser());
            return "20";
        }
        if(gameTable.pat()){
            gameTable.restart();
            return "40";
        }
        gameTable=ai.makeStep(gameTable,difficult);
        if(gameTable.isWin('2')){
            gameTable.restart();
            gameTable.getUser().def();
            userRepo.save(gameTable.getUser());
            return "30";
        }
        aiRepo.save(ai);
        return ai.getLastStep()+"";
    }
}
