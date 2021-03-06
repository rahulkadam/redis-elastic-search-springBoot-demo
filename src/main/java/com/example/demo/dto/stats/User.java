package com.example.demo.dto.stats;

import java.util.Random;
import java.util.UUID;

public class User {
    private String name;
    private Long id;

    public User() {
        id = new Random().nextLong();
    }
    public User(String name, Long id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
