package com.mhohos.EventManager.repository;

import com.mhohos.EventManager.model.Event;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends CrudRepository<Event, Long> {
    List<Event> findAll();
    Optional<Event> findById(Long id);
}
