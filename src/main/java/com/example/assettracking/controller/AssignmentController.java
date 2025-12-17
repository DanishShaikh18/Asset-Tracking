// File: src/main/java/com/example/assettracking/controller/AssignmentController.java
package com.example.assettracking.controller;

import com.example.assettracking.model.Asset;
import com.example.assettracking.model.Assignment;
import com.example.assettracking.model.User;
import com.example.assettracking.service.AssetService;
import com.example.assettracking.service.AssignmentService;
import com.example.assettracking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AssignmentController {
    
    @Autowired
    private AssignmentService assignmentService;
    
    @Autowired
    private AssetService assetService;
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/assign")
    public ResponseEntity<?> assignAsset(@RequestBody Map<String, Long> request) {
        Long assetId = request.get("assetId");
        Long userId = request.get("userId");
        
        Optional<Asset> assetOpt = assetService.getAssetById(assetId);
        if (assetOpt.isEmpty()) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Asset not found"));
        }
        
        Asset asset = assetOpt.get();
        if (!"AVAILABLE".equals(asset.getStatus())) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Asset is not available. Current status: " + asset.getStatus()));
        }
        
        Optional<User> userOpt = userService.getUserById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "User not found"));
        }
        
        User user = userOpt.get();
        Assignment assignment = assignmentService.assignAsset(asset, user);
        return ResponseEntity.ok(assignment);
    }
    
    @PostMapping("/return")
    public ResponseEntity<?> returnAsset(@RequestBody Map<String, Long> request) {
        Long assetId = request.get("assetId");
        
        Assignment assignment = assignmentService.returnAsset(assetId);
        
        if (assignment == null) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "No active assignment found for this asset"));
        }
        
        return ResponseEntity.ok(assignment);
    }
    
    @GetMapping("/history")
    public ResponseEntity<List<Assignment>> getAssignmentHistory() {
        return ResponseEntity.ok(assignmentService.getAllAssignments());
    }
}