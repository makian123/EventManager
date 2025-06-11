package com.mhohos.TaskManager.repository;

import com.mhohos.TaskManager.model.Task;
import com.mhohos.TaskManager.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNullApi;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findAll();
    Optional<User> findById(Long id);
}
