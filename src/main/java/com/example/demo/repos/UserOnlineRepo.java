package com.example.demo.repos;

import com.example.demo.entiy.GameTable;
import com.example.demo.entiy.UserOnline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserOnlineRepo extends JpaRepository<UserOnline,Long> {
    @Query("select userOnline from UserOnline userOnline where not user_id=:id")
    List<UserOnline> findByUsers(long id);
    @Query("delete from UserOnline where user_id=:id")
    void deleteById(long id);
}
