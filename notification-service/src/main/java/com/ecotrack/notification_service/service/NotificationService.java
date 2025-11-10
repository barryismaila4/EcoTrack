package com.ecotrack.notification_service.service;


import com.ecotrack.notification_service.entity.Notification;
import com.ecotrack.notification_service.repository.NotificationRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public Notification scheduleNotification(Notification notification) {
        Notification saved = notificationRepository.save(notification);

        // Envoyer à RabbitMQ
        rabbitTemplate.convertAndSend("general.notifications", saved);

        return saved;
    }

    public void sendWateringReminder(Long plantId, String plantName, Long userId) {
        String message = "🌱 Il est temps d'arroser votre " + plantName + " (Plante #" + plantId + ")";
        rabbitTemplate.convertAndSend("watering.notifications", message);

        // Sauvegarder la notification
        Notification notification = new Notification(
                message,
                "WATERING",
                plantId,
                userId,
                LocalDateTime.now()
        );
        notification.setSent(true);
        notificationRepository.save(notification);
    }

    // Planificateur pour les notifications programmées
    @Scheduled(fixedRate = 60000) // Toutes les minutes
    public void checkScheduledNotifications() {
        List<Notification> pending = notificationRepository
                .findBySentFalseAndScheduledDateBefore(LocalDateTime.now());

        for (Notification notification : pending) {
            rabbitTemplate.convertAndSend("general.notifications", notification);
            notification.setSent(true);
            notificationRepository.save(notification);
        }
    }

    public List<Notification> getUserNotifications(Long userId) {
        return notificationRepository.findByUserId(userId);
    }
}