// File: src/main/java/com/example/assettracking/model/Assignment.java
package com.example.assettracking.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "assignments")
public class Assignment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(nullable = false)
    private LocalDateTime assignedAt;
    
    @Column
    private LocalDateTime returnedAt;
    
    public Assignment() {
    }
    
    public Assignment(Asset asset, User user, LocalDateTime assignedAt) {
        this.asset = asset;
        this.user = user;
        this.assignedAt = assignedAt;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Asset getAsset() {
        return asset;
    }
    
    public void setAsset(Asset asset) {
        this.asset = asset;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }
    
    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }
    
    public LocalDateTime getReturnedAt() {
        return returnedAt;
    }
    
    public void setReturnedAt(LocalDateTime returnedAt) {
        this.returnedAt = returnedAt;
    }
    
    @Override
    public String toString() {
        return "Assignment{" +
                "id=" + id +
                ", asset=" + asset.getName() +
                ", user=" + user.getName() +
                ", assignedAt=" + assignedAt +
                ", returnedAt=" + returnedAt +
                '}';
    }
}