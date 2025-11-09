package com.ecotrack.plant_service.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "plants")
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
    private LocalDate purchaseDate;
    private String healthStatus = "GOOD"; // GOOD, NEEDS_CARE, CRITICAL
    private Long userId; // Référence à l'utilisateur

    // Constructeurs
    public Plant() {}

    public Plant(String name, String type, LocalDate purchaseDate, Long userId) {
        this.name = name;
        this.type = type;
        this.purchaseDate = purchaseDate;
        this.userId = userId;
    }

    // Getters/Setters...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public LocalDate getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(LocalDate purchaseDate) { this.purchaseDate = purchaseDate; }

    public String getHealthStatus() { return healthStatus; }
    public void setHealthStatus(String healthStatus) { this.healthStatus = healthStatus; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}