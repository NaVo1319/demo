package com.example.demo.entiy;

import com.example.demo.repos.RoomRepo;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Data
@Entity
public class GameRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nameRoom;
    private String action="000000000";
    @OneToOne()
    @JoinColumn(name="userhost_id", referencedColumnName = "id")
    private User userHost=null;
    @OneToOne()
    @JoinColumn(name="userpl_id", referencedColumnName = "id")
    private User userPl=null;
    private int stat=0;

}
