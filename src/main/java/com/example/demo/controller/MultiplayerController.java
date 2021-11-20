package com.example.demo.controller;

import com.example.demo.entiy.GameRoom;
import com.example.demo.entiy.User;
import com.example.demo.repos.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MultiplayerController {
    @Autowired
    RoomRepo roomRepo;
    //Меню мультиплеера
    @GetMapping("/multiplayerMenu")
    public String multGet(@AuthenticationPrincipal User user){
        GameRoom gameRoom=roomRepo.findByUserHost(user);
        if(gameRoom!=null){
            return "redirect:/room/"+gameRoom.getId();
        }
        gameRoom=roomRepo.findByUserPl(user);
        if(gameRoom!=null){
            return "redirect:/room/"+gameRoom.getId();
        }
        return "multiplayer";
    }
    //Создание комнаты
    @GetMapping("/createRoom")
    public String addRoomGet(){
        return "addRoom";
    }
    @PostMapping("/createRoom")
    public String addRoomPost(@AuthenticationPrincipal User user, @RequestParam String nameRoom){
        GameRoom gameRoom=new GameRoom();
        gameRoom.setNameRoom(nameRoom);
        gameRoom.setUserHost(user);
        gameRoom.setStat(0);
        roomRepo.save(gameRoom);

        return "redirect:/room/"+roomRepo.findByNameRoom(nameRoom).getId();
    }
    @GetMapping("/room/{id}")
    public String roomGet(@PathVariable Long id, Model model,@AuthenticationPrincipal User user){
        GameRoom gameRoom=roomRepo.findById(id).orElse(new GameRoom());
        model.addAttribute("nameRoom", gameRoom.getNameRoom());
        model.addAttribute("host",gameRoom.getUserHost());
        model.addAttribute("player",gameRoom.getUserPl());
        model.addAttribute("id",id);
        if(user.getId()!=gameRoom.getUserHost().getId()){
            model.addAttribute("exit",0);
            gameRoom.setUserPl(user);
            roomRepo.save(gameRoom);
        }else {
            model.addAttribute("exit",1);
        }
        return "room";
    }
    @PostMapping("/room/{id}")
    public String roomPost(@PathVariable Long id,@RequestParam String action, Model model){
        if(action.equals("delete")){
            roomRepo.deleteById(id);
        }
        return "redirect:/multiplayerMenu";
    }
    @PostMapping("/room/game")
    @ResponseBody
    public String roomGamePost(@RequestBody String action, Model model){
        return action;
    }
    //Подключение к созданной комнате
    @GetMapping("/joinRoom")
    public String joinRoomGet(Model model){
        List<GameRoom> rooms=roomRepo.findAll();
        if (rooms==null){
            model.addAttribute("rooms",new ArrayList<>());
        }
        model.addAttribute("rooms",rooms);
        return "joinRoom";
    }
}
