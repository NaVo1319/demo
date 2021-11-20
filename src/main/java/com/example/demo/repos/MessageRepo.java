package com.example.demo.repos;

import com.example.demo.entiy.MessageBD;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRepo extends JpaRepository<MessageBD,Long> {
    Optional<MessageBD> findById(Long id);
    List<MessageBD> findAllByAuthor(String author);
}
