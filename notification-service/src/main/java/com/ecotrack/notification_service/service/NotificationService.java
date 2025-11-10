package com.ecotrack.notification_service.service;

import com.ecotrack.notification_service.entity.Notification;
import com.ecotrack.notification_service.repository.NotificationRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // 🔔 Dernière notification envoyée
    private Notification lastNotification;

    private int demoCounter = 0;

    // ✅ 1. Créer une notification programmée
    public Notification scheduleNotification(Notification notification) {
        System.out.println("📅 Programmée: " + notification.getMessage());
        notification.setSent(false);
        return notificationRepository.save(notification);
    }

    // ✅ 2. Envoyer un rappel d’arrosage immédiat
    public void sendWateringReminder(Long plantId, String plantName, Long userId) {
        String message = "💧 Votre plante '" + plantName + "' (#" + plantId + ") a besoin d’eau !";
        Notification notification = new Notification(
                message, "WATERING", plantId, userId, LocalDateTime.now()
        );
        notification.setSent(true);
        Notification saved = notificationRepository.save(notification);
        rabbitTemplate.convertAndSend("general.notifications", saved);
        lastNotification = saved;
        System.out.println("✅ Arrosage envoyé: " + message);
    }

    // ✅ 3. Vérifie et envoie les notifications programmées
    @Scheduled(fixedRate = 30000)
    public void checkScheduledNotifications() {
        List<Notification> pending = notificationRepository
                .findBySentFalseAndScheduledDateBefore(LocalDateTime.now());

        for (Notification notification : pending) {
            System.out.println("🚀 Déclenchement programmé: " + notification.getMessage());
            rabbitTemplate.convertAndSend("general.notifications", notification);
            notification.setSent(true);
            notificationRepository.save(notification);
            lastNotification = notification;
        }
    }

    // ✅ 4. Envoi automatique d’un rappel toutes les 30s
    @Scheduled(fixedRate = 30000)
    public void sendAutoWaterReminder() {
        demoCounter++;
        String message = "🔔 Rappel automatique #" + demoCounter + ": votre plante a besoin d’eau 💦";
        Notification autoNotif = new Notification(
                message, "AUTO_WATER", 1L, 1L, LocalDateTime.now()
        );
        autoNotif.setSent(true);
        Notification saved = notificationRepository.save(autoNotif);
        rabbitTemplate.convertAndSend("general.notifications", saved);
        lastNotification = saved;
        System.out.println("🧪 Notification automatique envoyée: " + message);
    }

    // ✅ 5. Récupérer les notifications d’un utilisateur
    public List<Notification> getUserNotifications(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    // ✅ 6. Dernière notification envoyée
    public Optional<Notification> getLastNotification() {
        return Optional.ofNullable(lastNotification);
    }

    // ✅ 7. Compter toutes les notifications
    public long countNotifications() {
        return notificationRepository.count();
    }

    // ✅ 8. Compter les notifications par utilisateur
    public long countNotificationsByUser(Long userId) {
        return notificationRepository.countByUserId(userId);
    }
}
