package com.mhohos.eventManager.repository;

import com.mhohos.eventManager.model.Event;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends CrudRepository<Event, Long> {
    List<Event> findAll();
    Optional<Event> findById(Long id);
}
