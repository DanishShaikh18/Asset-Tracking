// File: src/main/java/com/example/assettracking/repository/AssignmentRepository.java
package com.example.assettracking.repository;

import com.example.assettracking.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByAssetIdAndReturnedAtIsNull(Long assetId);
}