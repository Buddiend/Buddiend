package com.buddiend.buddiend.models;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "chat_rooms")
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
    )
    private User user_id;

    @Column(nullable = false)
    private String slug;

    @ManyToOne
    @JoinColumn(
            name = "topic_id",
            referencedColumnName = "id"
    )
    private Topic topic_id;

    @ManyToMany
    @JoinTable(
            name = "chat_room_has_languages",
            joinColumns = @JoinColumn(
                    name = "chat_room_id",
                    referencedColumnName = "id",
                    updatable = false,
                    nullable = false),
            inverseJoinColumns = @JoinColumn(
                    name = "language_id",
                    referencedColumnName = "id",
                    updatable = false,
                    nullable = false))
    private List<Language> languages;

    public ChatRoom(String name, String description, Topic topic_id) {
        this.name = name;
        this.description = description;
        this.topic_id = topic_id;
    }
}
