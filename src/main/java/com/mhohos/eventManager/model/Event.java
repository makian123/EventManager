package com.mhohos.eventManager.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @ManyToMany(mappedBy="eventsAttending", fetch = FetchType.LAZY)
    private Set<User> usersAttending;

    public Event(){}
    public Event(String taskName){
        this.name = taskName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsersAttending() {
        return usersAttending;
    }

    public void setUsersAttending(Set<User> usersAttending) {
        this.usersAttending = usersAttending;
    }
}
