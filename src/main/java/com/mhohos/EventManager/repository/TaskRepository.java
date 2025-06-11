package com.mhohos.EventManager.repository;

import com.mhohos.EventManager.model.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends CrudRepository<Task, Long> {
    List<Task> findAll();
    Optional<Task> findById(Long id);
}
