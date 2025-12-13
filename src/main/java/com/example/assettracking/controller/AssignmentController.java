// File: src/main/java/com/example/assettracking/controller/AssignmentController.java
package com.example.assettracking.controller;

import com.example.assettracking.model.Asset;
import com.example.assettracking.model.Assignment;
import com.example.assettracking.model.User;
import com.example.assettracking.service.AssetService;
import com.example.assettracking.service.AssignmentService;
import com.example.assettracking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class AssignmentController {
    
    @Autowired
    private AssignmentService assignmentService;
    
    @Autowired
    private AssetService assetService;
    
    @Autowired
    private UserService userService;
    
    public void assignAsset(Scanner scanner) {
        System.out.print("Enter Asset ID to assign: ");
        Long assetId = Long.parseLong(scanner.nextLine());
        
        Optional<Asset> assetOpt = assetService.getAssetById(assetId);
        if (assetOpt.isEmpty()) {
            System.out.println("✗ Asset not found.");
            return;
        }
        
        Asset asset = assetOpt.get();
        if (!"AVAILABLE".equals(asset.getStatus())) {
            System.out.println("✗ Asset is not available. Current status: " + asset.getStatus());
            return;
        }
        
        System.out.print("Enter User ID to assign to: ");
        Long userId = Long.parseLong(scanner.nextLine());
        
        Optional<User> userOpt = userService.getUserById(userId);
        if (userOpt.isEmpty()) {
            System.out.println("✗ User not found.");
            return;
        }
        
        User user = userOpt.get();
        Assignment assignment = assignmentService.assignAsset(asset, user);
        System.out.println("✓ Asset assigned successfully: " + assignment);
    }
    
    public void returnAsset(Scanner scanner) {
        System.out.print("Enter Asset ID to return: ");
        Long assetId = Long.parseLong(scanner.nextLine());
        
        Assignment assignment = assignmentService.returnAsset(assetId);
        
        if (assignment == null) {
            System.out.println("✗ No active assignment found for this asset.");
            return;
        }
        
        System.out.println("✓ Asset returned successfully: " + assignment);
    }
    
    public void viewAssignmentHistory() {
        List<Assignment> assignments = assignmentService.getAllAssignments();
        
        if (assignments.isEmpty()) {
            System.out.println("No assignment history found.");
            return;
        }
        
        System.out.println("\n=== ASSIGNMENT HISTORY ===");
        for (Assignment assignment : assignments) {
            String status = assignment.getReturnedAt() == null ? "ACTIVE" : "RETURNED";
            System.out.println("ID: " + assignment.getId() + 
                             " | Asset: " + assignment.getAsset().getName() + 
                             " | User: " + assignment.getUser().getName() + 
                             " | Assigned: " + assignment.getAssignedAt() + 
                             " | Returned: " + (assignment.getReturnedAt() != null ? assignment.getReturnedAt() : "N/A") +
                             " | Status: " + status);
        }
        System.out.println("==========================\n");
    }
}