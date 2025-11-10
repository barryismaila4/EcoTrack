package com.ecotrack.plant_service.service;

import com.ecotrack.plant_service.client.UserServiceClient;
import com.ecotrack.plant_service.entity.Plant;
import com.ecotrack.plant_service.repository.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlantService {

    private final PlantRepository plantRepository;
    private final UserServiceClient userServiceClient; // AJOUT

    @Autowired
    public PlantService(PlantRepository plantRepository, UserServiceClient userServiceClient) {
        this.plantRepository = plantRepository;
        this.userServiceClient = userServiceClient; // AJOUT
    }

    // MÉTHODES EXISTANTES
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

    // NOUVELLES MÉTHODES AVEC COMMUNICATION FEIGN
    public List<Map<String, Object>> getAllPlantsWithUserInfo() {
        List<Plant> plants = plantRepository.findAll();

        return plants.stream().map(plant -> {
            Map<String, Object> plantWithUser = new HashMap<>();
            plantWithUser.put("id", plant.getId());
            plantWithUser.put("name", plant.getName());
            plantWithUser.put("type", plant.getType());
            plantWithUser.put("healthStatus", plant.getHealthStatus());
            plantWithUser.put("purchaseDate", plant.getPurchaseDate());
            plantWithUser.put("userId", plant.getUserId());

            // Récupérer les infos utilisateur via Feign
            try {
                Map<String, Object> userInfo = userServiceClient.getUserById(plant.getUserId());
                plantWithUser.put("user", userInfo);
            } catch (Exception e) {
                plantWithUser.put("user", Map.of("error", "User not found or service unavailable"));
            }

            return plantWithUser;
        }).collect(Collectors.toList());
    }

    public Map<String, Object> getPlantWithUserInfo(Long id) {
        Plant plant = plantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plant not found"));

        Map<String, Object> plantWithUser = new HashMap<>();
        plantWithUser.put("id", plant.getId());
        plantWithUser.put("name", plant.getName());
        plantWithUser.put("type", plant.getType());
        plantWithUser.put("healthStatus", plant.getHealthStatus());
        plantWithUser.put("purchaseDate", plant.getPurchaseDate());
        plantWithUser.put("userId", plant.getUserId());

        // Récupérer les infos utilisateur via Feign
        try {
            Map<String, Object> userInfo = userServiceClient.getUserById(plant.getUserId());
            plantWithUser.put("user", userInfo);
        } catch (Exception e) {
            plantWithUser.put("user", Map.of("error", "User not found or service unavailable"));
        }

        return plantWithUser;
    }
}