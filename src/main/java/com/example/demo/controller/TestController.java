package com.example.demo.controller;

import com.example.demo.entiy.AI;
import com.example.demo.entiy.GameTable;
import com.example.demo.entiy.User;
import com.example.demo.repos.AiRepo;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Controller
public class TestController {
    @Autowired
    AiRepo aiRepo;
    @RequestMapping(value = "/button", method = RequestMethod.GET)
    @ResponseBody
    public String getRequest(@RequestHeader("stat") String stat, Model model){
        System.out.println(stat);
        ObjectMapper json=new ObjectMapper();
        GameTable game=new GameTable();
        try {
            game=json.readValue(stat,GameTable.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(game.toString());
        return stat;
    }
    @GetMapping("/createAI")
    public String createAI(){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        AI createAi=new AI();
        createAi.setNameAi("CreateAi - "+timeStamp);
        aiRepo.save(createAi);
        return "redirect:/";
    }
}
