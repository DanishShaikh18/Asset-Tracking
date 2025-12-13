// File: src/main/java/com/example/assettracking/controller/AssetController.java
package com.example.assettracking.controller;

import com.example.assettracking.model.Asset;
import com.example.assettracking.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Scanner;

@Component
public class AssetController {
    
    @Autowired
    private AssetService assetService;
    
    public void addAsset(Scanner scanner) {
        System.out.print("Enter asset name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter asset type (e.g., Laptop, Monitor, Mouse): ");
        String type = scanner.nextLine();
        
        Asset asset = assetService.addAsset(name, type);
        System.out.println("✓ Asset added successfully: " + asset);
    }
    
    public void viewAllAssets() {
        List<Asset> assets = assetService.getAllAssets();
        
        if (assets.isEmpty()) {
            System.out.println("No assets found.");
            return;
        }
        
        System.out.println("\n=== ALL ASSETS ===");
        for (Asset asset : assets) {
            System.out.println("ID: " + asset.getId() + 
                             " | Name: " + asset.getName() + 
                             " | Type: " + asset.getType() + 
                             " | Status: " + asset.getStatus());
        }
        System.out.println("==================\n");
    }
}