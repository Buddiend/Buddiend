package com.buddiend.buddiend.models;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.utility.RandomString;

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
    private Topic topic;

    @ManyToOne
    @JoinColumn(
            name = "language_id",
            referencedColumnName = "id"
    )
    private Language language;

    @Column(nullable = false)
    private String meetingId;

    public ChatRoom(String name, String description, Topic topic_id, User user_id, Language language, String meetingId) {
        this.name = name;
        this.description = description;
        this.topic = topic_id;
        this.user_id = user_id;
        this.language = language;
        this.slug = RandomString.make(10);
        this.meetingId = meetingId;
    }
}
