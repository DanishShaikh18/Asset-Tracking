// File: src/main/java/com/example/assettracking/repository/AssetRepository.java
package com.example.assettracking.repository;

import com.example.assettracking.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {
}