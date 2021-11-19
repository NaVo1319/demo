package com.example.demo.repos;

import com.example.demo.entiy.AI;
import com.example.demo.entiy.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AiRepo extends JpaRepository<AI,Long> {
    AI findByNameAi(String nameAi);
    Optional<AI> findById(Long id);
}
