package com.ecotrack.plant_service.controller;

import com.ecotrack.plant_service.entity.Plant;
import com.ecotrack.plant_service.service.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/plants")
public class PlantController {

    private final PlantService plantService;

    @Autowired
    public PlantController(PlantService plantService) {
        this.plantService = plantService;
    }

    // ENDPOINTS EXISTANTS
    @GetMapping
    public List<Plant> getAllPlants() {
        return plantService.getAllPlants();
    }

    @GetMapping("/user/{userId}")
    public List<Plant> getPlantsByUser(@PathVariable Long userId) {
        return plantService.getPlantsByUser(userId);
    }

    @PostMapping
    public Plant createPlant(@RequestBody Plant plant) {
        return plantService.createPlant(plant);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plant> getPlantById(@PathVariable Long id) {
        return plantService.getPlantById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Plant> updatePlant(@PathVariable Long id, @RequestBody Plant plantDetails) {
        try {
            Plant updatedPlant = plantService.updatePlant(id, plantDetails);
            return ResponseEntity.ok(updatedPlant);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlant(@PathVariable Long id) {
        plantService.deletePlant(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/hello")
    public ResponseEntity<?> hello() {
        return ResponseEntity.ok(Map.of("message", "Hello from Plant Service!"));
    }

    // NOUVEAUX ENDPOINTS AVEC COMMUNICATION FEIGN
    @GetMapping("/with-users")
    public List<Map<String, Object>> getAllPlantsWithUserInfo() {
        return plantService.getAllPlantsWithUserInfo();
    }

    @GetMapping("/{id}/with-user")
    public ResponseEntity<Map<String, Object>> getPlantWithUserInfo(@PathVariable Long id) {
        try {
            Map<String, Object> plantWithUser = plantService.getPlantWithUserInfo(id);
            return ResponseEntity.ok(plantWithUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}