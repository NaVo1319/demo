package com.example.demo.entiy;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class UserOnline {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
