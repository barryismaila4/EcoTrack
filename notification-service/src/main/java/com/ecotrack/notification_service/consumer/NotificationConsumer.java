package com.ecotrack.notification_service.consumer;

import com.ecotrack.notification_service.entity.Notification;
import com.ecotrack.notification_service.repository.NotificationRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    @Autowired
    private NotificationRepository notificationRepository;

    @RabbitListener(queues = "watering.notifications")
    public void receiveWateringNotification(String message) {
        System.out.println("🚰 RAPPEL ARROSAGE: " + message);
    }

    @RabbitListener(queues = "general.notifications")
    public void receiveGeneralNotification(Notification notification) {
        System.out.println("📢 NOTIFICATION REÇUE: " + notification.getMessage());

        // ✅ IMPORTANT: Mettre à jour le statut dans la base
        if (!notification.isSent()) {
            notification.setSent(true);
            notificationRepository.save(notification);
        }

        System.out.println("💾 Statut mis à jour pour: " + notification.getId());
    }
}