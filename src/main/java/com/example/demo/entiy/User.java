package com.example.demo.entiy;

import com.example.demo.repos.UserRepo;
import com.sun.istack.NotNull;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @Column(length = 20)
    private String username;
    @NotNull
    @Column(length = 20)
    private String password;
    private  boolean active;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
    private int rating;
    @OneToOne
    @JoinColumn(name="ai_id")
    private AI selectedAi;
    private int difficultAi=1;
    public User(){}
    public User(String author){
        username=author;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    public void rating(boolean win){
        if (rating>1 && !win){
            rating--;
        }
        else {
            rating++;
        }
    }
}
