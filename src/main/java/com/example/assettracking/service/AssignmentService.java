// File: src/main/java/com/example/assettracking/service/AssignmentService.java
package com.example.assettracking.service;

import com.example.assettracking.model.Asset;
import com.example.assettracking.model.Assignment;
import com.example.assettracking.model.User;
import com.example.assettracking.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AssignmentService {
    
    @Autowired
    private AssignmentRepository assignmentRepository;
    
    @Autowired
    private AssetService assetService;
    
    public Assignment assignAsset(Asset asset, User user) {
        Assignment assignment = new Assignment(asset, user, LocalDateTime.now());
        Assignment saved = assignmentRepository.save(assignment);
        assetService.updateAssetStatus(asset, "ASSIGNED");
        return saved;
    }
    
    public Assignment returnAsset(Long assetId) {
        List<Assignment> activeAssignments = assignmentRepository.findByAssetIdAndReturnedAtIsNull(assetId);
        
        if (activeAssignments.isEmpty()) {
            return null;
        }
        
        Assignment assignment = activeAssignments.get(0);
        assignment.setReturnedAt(LocalDateTime.now());
        Assignment saved = assignmentRepository.save(assignment);
        
        assetService.updateAssetStatus(assignment.getAsset(), "AVAILABLE");
        return saved;
    }
    
    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }
}