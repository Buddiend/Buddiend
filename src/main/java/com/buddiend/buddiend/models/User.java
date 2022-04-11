package com.buddiend.buddiend.models;

import com.buddiend.buddiend.models.enumerations.Role;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private String profile_picture;

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

    public User(String email, String username, String name, String password, Role role) {
        this.email = email;
        this.role = role;
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public User() {

    }

    public void setDeleteDate() {
        this.deleted_at = LocalDateTime.now();
    }

    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;
    private boolean isEnabled = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(role);
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
