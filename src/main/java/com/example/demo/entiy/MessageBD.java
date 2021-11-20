package com.example.demo.entiy;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class MessageBD {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content;
    private String author;
    public MessageBD(){}
    public MessageBD(String content, String author) {
        this.content = content;
        this.author = author;
    }
}
