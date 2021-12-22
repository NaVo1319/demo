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
public class AiController {
    @Autowired
    AiRepo aiRepo;
    @GetMapping("/createAI")
    public String createAI(){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        AI createAi=new AI();
        createAi.setNameAi("CreateAi - "+timeStamp);
        aiRepo.save(createAi);
        return "redirect:/";
    }
}
