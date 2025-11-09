package com.ecotrack.plant_service.repository;
import com.ecotrack.plant_service.entity.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlantRepository extends JpaRepository<Plant, Long> {
    List<Plant> findByUserId(Long userId);
    List<Plant> findByHealthStatus(String healthStatus);
}
