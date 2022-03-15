package com.buddiend.buddiend.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "topics")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    public Topic() {
    }

    public Topic(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
