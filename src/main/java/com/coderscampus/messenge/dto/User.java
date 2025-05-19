package com.coderscampus.messenge.dto;

import jakarta.persistence.*;

@Entity
//@Table(name = "users")
public class User {

    private Long userId;
    private String username;
    private String password;

    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
//    @Column(unique = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }


}
