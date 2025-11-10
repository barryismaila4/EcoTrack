package com.ecotrack.notification_service.controller;


import com.ecotrack.notification_service.entity.Notification;
import com.ecotrack.notification_service.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "http://localhost:4200")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public Notification createNotification(@RequestBody Notification notification) {
        return notificationService.scheduleNotification(notification);
    }

    @PostMapping("/watering/{plantId}")
    public ResponseEntity<?> sendWateringReminder(
            @PathVariable Long plantId,
            @RequestBody Map<String, String> request) {

        String plantName = request.get("plantName");
        Long userId = Long.parseLong(request.get("userId"));

        notificationService.sendWateringReminder(plantId, plantName, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}")
    public List<Notification> getUserNotifications(@PathVariable Long userId) {
        return notificationService.getUserNotifications(userId);
    }

    @GetMapping("/hello")
    public ResponseEntity<?> hello() {
        return ResponseEntity.ok(Map.of("message", "Hello from Notification Service!"));
    }
}