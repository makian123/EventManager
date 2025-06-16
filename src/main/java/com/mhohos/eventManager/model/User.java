package com.mhohos.eventManager.model;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid")
    private UUID id;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private boolean admin;
    @OneToMany(mappedBy = "owner")
    private Set<Event> ownedEvents;
    @ManyToMany(fetch = FetchType.LAZY)
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
        this.eventsAttending = new HashSet<>();
    }

    public void addAttendance(Event event){
        eventsAttending.add(event);
        event.getUsersAttending().add(this);
    }
    public void removeAttendance(Event event){
        eventsAttending.remove(event);
        event.getUsersAttending().remove(this);
    }

    public void addOwnedEvent(Event event){
        ownedEvents.add(event);
        event.setOwner(this);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

    public Set<Event> getOwnedEvents() {
        return ownedEvents;
    }

    public void setOwnedEvents(Set<Event> ownedEvents) {
        this.ownedEvents = ownedEvents;
    }
}
