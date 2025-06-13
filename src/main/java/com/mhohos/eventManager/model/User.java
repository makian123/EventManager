package com.mhohos.eventManager.model;

import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private boolean admin;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "attendance",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "eventId")
    )
    private Set<Event> eventsAttending;

    public User(){}
    public User(String name, String password, boolean admin){
        this.username = name;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
        this.admin = admin;
    }

    public void addAttendance(Event event){
        eventsAttending.add(event);
        event.getUsersAttending().add(this);
    }
    public void removeAttendance(Event event){
        eventsAttending.remove(event);
        event.getUsersAttending().remove(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Set<Event> getEventsAttending() {
        return eventsAttending;
    }

    public void setEventsAttending(Set<Event> eventsAttending) {
        this.eventsAttending = eventsAttending;
    }
}
