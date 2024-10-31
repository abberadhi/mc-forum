package com.abberadhi.mc_forum.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
 
    @Column(nullable = false, unique = true)
    private String username;

    @Column(columnDefinition = "TEXT", nullable = true, unique = false)
    private String description;

    @Column(name = "date_joined", nullable = false)
    private LocalDateTime dateJoined;

    @Column (nullable = false)
    private String password;

    public User(){}

    // // Constructor with all fields (except ID since it is auto-generated)
    // public Users (String username, String description, LocalDateTime dateJoined, String password) {
    //     this.username = username;
    //     this.description = description;
    //     this.dateJoined = dateJoined;
    //     this.password = password;
    // }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(LocalDateTime dateJoined) {
        this.dateJoined = dateJoined;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
