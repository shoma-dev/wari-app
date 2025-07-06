package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private Group group;

    // getter
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Group getGroup() {
        return group;
    }

    // setter
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
