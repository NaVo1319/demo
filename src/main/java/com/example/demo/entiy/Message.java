package com.example.demo.entiy;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content;
    @ManyToOne
    @JoinColumn(name = "author_id")
    @NonNull
    private User author;
    public Message(){}
    public Message(String content, User author) {
        this.content = content;
        this.author = author;
    }

    public String getAuthorName() {
        return author.getUsername();
    }
}
