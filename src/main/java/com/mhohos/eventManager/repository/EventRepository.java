package com.mhohos.eventManager.repository;

import com.mhohos.eventManager.model.Event;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventRepository extends CrudRepository<Event, UUID> {
    List<Event> findAll(Pageable pageable);
    Optional<Event> findById(UUID id);
}
