// File: src/main/java/com/example/assettracking/controller/UserController.java
package com.example.assettracking.controller;

import com.example.assettracking.model.User;
import com.example.assettracking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
    
    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody Map<String, String> request) {
        try {
            String name = request.get("name");
            String email = request.get("email");
            User user = userService.addUser(name, email);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Email already exists or invalid data"));
        }
    }
    
    @GetMapping("/count")
    public ResponseEntity<Long> getTotalCount() {
        long count = userService.getAllUsers().size();
        return ResponseEntity.ok(count);
    }
}