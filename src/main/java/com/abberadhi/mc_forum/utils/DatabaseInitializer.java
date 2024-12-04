package com.abberadhi.mc_forum.utils;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.abberadhi.mc_forum.model.BikeModelEntity;
import com.abberadhi.mc_forum.repository.BikeModelRepository;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final BikeModelRepository bikeModelRepository;

    public DatabaseInitializer(BikeModelRepository bikeModelRepository) {
        this.bikeModelRepository = bikeModelRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        String filePath = new ClassPathResource("data/bikes-list.csv").getFile().getAbsolutePath();

        List<BikeModelEntity> bikeEntities = CsvUtils.readBikesFromCsv(filePath);

        if (bikeModelRepository.count() != 0) {
            return;
        }

        for (BikeModelEntity bike : bikeEntities) {
            bikeModelRepository.save(bike);
        }
    }
}
