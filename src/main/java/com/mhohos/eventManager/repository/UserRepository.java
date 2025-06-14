package com.mhohos.eventManager.repository;

import com.mhohos.eventManager.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
    List<User> findAll();
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
    Optional<User> findById(UUID id);
}
