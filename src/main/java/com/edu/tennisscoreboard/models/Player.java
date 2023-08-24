package com.edu.tennisscoreboard.models;

import jakarta.persistence.*;

@Entity
@Table(name="Players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String name;

    protected Player() {}
    public Player(String name) {this.name = name;}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Player[id=" + Long.toString(id) + ", name=" + name + "]";
    }
}
