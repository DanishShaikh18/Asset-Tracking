// File: src/main/java/com/example/assettracking/controller/UserController.java
package com.example.assettracking.controller;

import com.example.assettracking.model.User;
import com.example.assettracking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Scanner;

@Component
public class UserController {
    
    @Autowired
    private UserService userService;
    
    public void addUser(Scanner scanner) {
        System.out.print("Enter user name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter user email: ");
        String email = scanner.nextLine();
        
        try {
            User user = userService.addUser(name, email);
            System.out.println("✓ User added successfully: " + user);
        } catch (Exception e) {
            System.out.println("✗ Error adding user. Email might already exist.");
        }
    }
    
    public void viewAllUsers() {
        List<User> users = userService.getAllUsers();
        
        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }
        
        System.out.println("\n=== ALL USERS ===");
        for (User user : users) {
            System.out.println("ID: " + user.getId() + 
                             " | Name: " + user.getName() + 
                             " | Email: " + user.getEmail());
        }
        System.out.println("=================\n");
    }
}