package com.ecotrack.notification_service.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;
    private String type; // WATERING, FERTILIZING, REPOTTING
    private Long plantId;
    private Long userId;
    private LocalDateTime scheduledDate;
    private boolean sent = false;

    // Constructeurs, Getters/Setters...
    public Notification() {}

    public Notification(String message, String type, Long plantId, Long userId, LocalDateTime scheduledDate) {
        this.message = message;
        this.type = type;
        this.plantId = plantId;
        this.userId = userId;
        this.scheduledDate = scheduledDate;
    }

    // Getters/Setters...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Long getPlantId() { return plantId; }
    public void setPlantId(Long plantId) { this.plantId = plantId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public LocalDateTime getScheduledDate() { return scheduledDate; }
    public void setScheduledDate(LocalDateTime scheduledDate) { this.scheduledDate = scheduledDate; }

    public boolean isSent() { return sent; }
    public void setSent(boolean sent) { this.sent = sent; }
}