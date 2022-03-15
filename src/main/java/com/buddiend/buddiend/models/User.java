package com.buddiend.buddiend.models;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String profile_picture;

    @Column(nullable = false)
    private String location;

    @CreatedDate
    private LocalDateTime created_at;

    @LastModifiedDate
    private LocalDateTime updated_at;

    private LocalDateTime deleted_at;

    @ManyToMany
    @JoinTable(
            name = "users_interested_in_topics",
            joinColumns = @JoinColumn(
                        name = "user_id",
                        referencedColumnName = "id",
                        updatable = false,
                        nullable = false),
            inverseJoinColumns = @JoinColumn(
                        name = "topic_id",
                        referencedColumnName = "id",
                        updatable = false,
                        nullable = false))
    private List<Topic> topicsList;

    public User() {
    }


    public void setDeleteDate(){
        this.deleted_at = LocalDateTime.now();
    }
}
