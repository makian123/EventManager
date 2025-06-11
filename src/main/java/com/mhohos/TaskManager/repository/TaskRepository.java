package com.mhohos.TaskManager.repository;

import com.mhohos.TaskManager.model.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends CrudRepository<Task, Long> {
    List<Task> findAll();
    Optional<Task> findById(Long id);
}
