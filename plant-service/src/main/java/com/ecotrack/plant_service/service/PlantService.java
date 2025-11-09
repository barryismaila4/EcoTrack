package com.ecotrack.plant_service.service;


import com.ecotrack.plant_service.entity.Plant;
import com.ecotrack.plant_service.repository.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PlantService {

    private final PlantRepository plantRepository;

    @Autowired
    public PlantService(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    public List<Plant> getAllPlants() {
        return plantRepository.findAll();
    }

    public List<Plant> getPlantsByUser(Long userId) {
        return plantRepository.findByUserId(userId);
    }

    public Plant createPlant(Plant plant) {
        return plantRepository.save(plant);
    }

    public Optional<Plant> getPlantById(Long id) {
        return plantRepository.findById(id);
    }

    public Plant updatePlant(Long id, Plant plantDetails) {
        Plant plant = plantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plant not found"));

        plant.setName(plantDetails.getName());
        plant.setType(plantDetails.getType());
        plant.setHealthStatus(plantDetails.getHealthStatus());

        return plantRepository.save(plant);
    }

    public void deletePlant(Long id) {
        plantRepository.deleteById(id);
    }
}