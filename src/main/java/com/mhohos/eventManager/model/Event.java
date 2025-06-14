package com.mhohos.eventManager.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "name")
    private String name;
    @ManyToOne(targetEntity = User.class)
    private User owner;
    @ManyToMany(mappedBy="eventsAttending", fetch = FetchType.LAZY)
    private Set<User> usersAttending;
    @Column
    private Date startDate;

    public Event(){}
    public Event(User owner, String taskName, Date startDate){
        this.owner = owner;
        this.name = taskName;
        this.startDate = startDate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
