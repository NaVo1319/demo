package com.example.demo.repos;

import com.example.demo.entiy.GameTable;
import com.example.demo.entiy.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Table;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface TableRepo extends JpaRepository<GameTable,Long> {
    Optional<GameTable> findById(Long id);
    @Query("select user from GameTable user where user_id=:id")
    GameTable findByUserId(long id);
    @Transactional
    @Modifying
    @Query("delete from GameTable where id=:id")
    void deleteById(long id);
}