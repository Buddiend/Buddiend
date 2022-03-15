package com.buddiend.buddiend.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "languages")
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Language() {
    }

    public Language(String name) {
        this.name = name;
    }
}
