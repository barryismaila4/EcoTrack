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
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // ✅ Créer une notification programmée
    @PostMapping
    public Notification createNotification(@RequestBody Notification notification) {
        return notificationService.scheduleNotification(notification);
    }

    // ✅ Envoyer un rappel d’arrosage immédiat
    @PostMapping("/watering/{plantId}")
    public ResponseEntity<?> sendWateringReminder(
            @PathVariable Long plantId,
            @RequestBody Map<String, String> request) {

        String plantName = request.get("plantName");
        Long userId = Long.parseLong(request.get("userId"));
        notificationService.sendWateringReminder(plantId, plantName, userId);
        return ResponseEntity.ok().build();
    }

    // ✅ Liste de toutes les notifications d’un utilisateur
    @GetMapping("/user/{userId}")
    public List<Notification> getUserNotifications(@PathVariable Long userId) {
        return notificationService.getUserNotifications(userId);
    }

    // ✅ Récupérer la dernière notification envoyée
    @GetMapping("/last")
    public ResponseEntity<?> getLastNotification() {
        return notificationService.getLastNotification()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    // ✅ Nombre total de notifications (tous utilisateurs confondus)
    @GetMapping("/count")
    public long getTotalNotifications() {
        return notificationService.countNotifications();
    }

    // ✅ Nombre total de notifications par utilisateur
    @GetMapping("/count/{userId}")
    public long getTotalNotificationsByUser(@PathVariable Long userId) {
        return notificationService.countNotificationsByUser(userId);
    }
}
