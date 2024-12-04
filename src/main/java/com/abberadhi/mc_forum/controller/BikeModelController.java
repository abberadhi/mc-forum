package com.abberadhi.mc_forum.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abberadhi.mc_forum.model.BikeModelEntity;
import com.abberadhi.mc_forum.repository.BikeModelRepository;

@RestController
@RequestMapping("/api/bikes")
public class BikeModelController {

    private final BikeModelRepository bikeModelRepository;

    public BikeModelController(BikeModelRepository bikeModelRepository) {
        this.bikeModelRepository = bikeModelRepository;
    }

    @GetMapping("/models/{brand}")
    public ResponseEntity<List<BikeModelEntity>> getAllBikeModelsByBrand(@PathVariable String brand) {
        List<BikeModelEntity> brands = bikeModelRepository.findByBrand(brand);
        return new ResponseEntity<>(brands, HttpStatus.OK);
    }

    @GetMapping("/brands")
    public ResponseEntity<List<String>> getAllBikeBrands() {
        List<String> brands = bikeModelRepository.findBrands();
        return new ResponseEntity<>(brands, HttpStatus.OK);
    }

}
