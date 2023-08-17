package com.example.jwtsecurity.repository;

import com.example.jwtsecurity.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
   Optional<UserEntity> findByName(String username);

}
