package com.ecotrack.notification_service.repository;

import com.ecotrack.notification_service.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // ✅ AJOUTEZ CETTE MÉTHODE
    List<Notification> findByUserId(Long userId);

    // ✅ ET CES MÉTHODES POUR LE SCHEDULER
    List<Notification> findBySentFalseAndScheduledDateBefore(java.time.LocalDateTime date);
    long countByUserId(Long userId);

}