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
        // Ici on pourrait envoyer un email, SMS, etc.
    }

    @RabbitListener(queues = "general.notifications")
    public void receiveGeneralNotification(Notification notification) {
        System.out.println("📢 NOTIFICATION: " + notification.getMessage() +
                " pour la plante " + notification.getPlantId());

        // Marquer comme envoyée
        notification.setSent(true);
        notificationRepository.save(notification);
    }
}