// File: src/main/java/com/example/assettracking/repository/UserRepository.java
package com.example.assettracking.repository;

import com.example.assettracking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}