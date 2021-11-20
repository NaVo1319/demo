package com.example.demo.repos;

import com.example.demo.entiy.AI;
import com.example.demo.entiy.GameRoom;
import com.example.demo.entiy.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepo extends JpaRepository<GameRoom,Long> {
    GameRoom findByNameRoom(String nameRoom);
    Optional<GameRoom> findById(Long id);
    GameRoom findByUserHost(User userHost);
    GameRoom findByUserPl(User userPl);
    long deleteById(long id);
}
