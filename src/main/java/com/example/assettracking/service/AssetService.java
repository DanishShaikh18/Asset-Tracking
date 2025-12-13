// File: src/main/java/com/example/assettracking/service/AssetService.java
package com.example.assettracking.service;

import com.example.assettracking.model.Asset;
import com.example.assettracking.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AssetService {
    
    @Autowired
    private AssetRepository assetRepository;
    
    public Asset addAsset(String name, String type) {
        Asset asset = new Asset(name, type, "AVAILABLE");
        return assetRepository.save(asset);
    }
    
    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }
    
    public Optional<Asset> getAssetById(Long id) {
        return assetRepository.findById(id);
    }
    
    public void updateAssetStatus(Asset asset, String status) {
        asset.setStatus(status);
        assetRepository.save(asset);
    }
}