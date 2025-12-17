// File: src/main/java/com/example/assettracking/controller/AssetController.java
package com.example.assettracking.controller;

import com.example.assettracking.model.Asset;
import com.example.assettracking.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/assets")
@CrossOrigin(origins = "*")
public class AssetController {
    
    @Autowired
    private AssetService assetService;
    
    @GetMapping
    public ResponseEntity<List<Asset>> getAllAssets() {
        return ResponseEntity.ok(assetService.getAllAssets());
    }
    
    @PostMapping
    public ResponseEntity<Asset> addAsset(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        String type = request.get("type");
        Asset asset = assetService.addAsset(name, type);
        return ResponseEntity.ok(asset);
    }
    
    @GetMapping("/count")
    public ResponseEntity<Long> getTotalCount() {
        long count = assetService.getAllAssets().size();
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/available/count")
    public ResponseEntity<Long> getAvailableCount() {
        long count = assetService.getAllAssets().stream()
            .filter(a -> "AVAILABLE".equals(a.getStatus()))
            .count();
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/assigned/count")
    public ResponseEntity<Long> getAssignedCount() {
        long count = assetService.getAllAssets().stream()
            .filter(a -> "ASSIGNED".equals(a.getStatus()))
            .count();
        return ResponseEntity.ok(count);
    }
}