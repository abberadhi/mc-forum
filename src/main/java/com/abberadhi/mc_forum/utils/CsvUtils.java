package com.abberadhi.mc_forum.utils;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.abberadhi.mc_forum.model.BikeModelEntity;
import com.opencsv.CSVReader;

public class CsvUtils {

    public static List<BikeModelEntity> readBikesFromCsv(String filePath) {
        List<BikeModelEntity> bikes = new ArrayList();
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                BikeModelEntity bike = new BikeModelEntity();
                bike.setBrand(line[0].strip());
                bike.setModel(line[1].strip());
                bikes.add(bike);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bikes;
    }
}
