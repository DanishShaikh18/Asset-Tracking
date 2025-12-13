// File: src/main/java/com/example/assettracking/AssetTrackingApplication.java
package com.example.assettracking;

import com.example.assettracking.controller.AssetController;
import com.example.assettracking.controller.AssignmentController;
import com.example.assettracking.controller.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Scanner;

@SpringBootApplication
public class AssetTrackingApplication implements CommandLineRunner {
    
    @Autowired
    private AssetController assetController;
    
    @Autowired
    private UserController userController;
    
    @Autowired
    private AssignmentController assignmentController;
    
    public static void main(String[] args) {
        SpringApplication.run(AssetTrackingApplication.class, args);
    }
    
    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        
        System.out.println("\n========================================");
        System.out.println("  ASSET TRACKING SYSTEM");
        System.out.println("========================================\n");
        
        while (running) {
            displayMenu();
            
            try {
                System.out.print("Enter your choice: ");
                int choice = Integer.parseInt(scanner.nextLine());
                System.out.println();
                
                switch (choice) {
                    case 1:
                        assetController.addAsset(scanner);
                        break;
                    case 2:
                        assetController.viewAllAssets();
                        break;
                    case 3:
                        userController.addUser(scanner);
                        break;
                    case 4:
                        assetController.viewAllAssets();
                        userController.viewAllUsers();
                        assignmentController.assignAsset(scanner);
                        break;
                    case 5:
                        assetController.viewAllAssets();
                        assignmentController.returnAsset(scanner);
                        break;
                    case 6:
                        assignmentController.viewAssignmentHistory();
                        break;
                    case 0:
                        System.out.println("Exiting Asset Tracking System. Goodbye!");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
                
                if (running) {
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                }
                
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        
        scanner.close();
    }
    
    private void displayMenu() {
        System.out.println("\n========================================");
        System.out.println("              MAIN MENU");
        System.out.println("========================================");
        System.out.println("1. Add Asset");
        System.out.println("2. View All Assets");
        System.out.println("3. Add User");
        System.out.println("4. Assign Asset");
        System.out.println("5. Return Asset");
        System.out.println("6. View Assignment History");
        System.out.println("0. Exit");
        System.out.println("========================================");
    }
}