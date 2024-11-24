package com.shirloin.suggestion_api.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.shirloin.suggestion_api.model.City;

@Component
public class DataLoader {
    public List<City> loadCitiesFromTSV() {
        List<City> cities = new ArrayList<>();
        String filePath = "/data/cities_canada-usa.tsv";

        try {
            InputStream inputStream = getClass().getResourceAsStream(filePath);
            if (inputStream == null) {
                throw new RuntimeException("File not found: " + filePath);
            }

            BufferedReader TSVFile = new BufferedReader(new InputStreamReader(inputStream));
            TSVFile.readLine();
            String line;

            while ((line = TSVFile.readLine()) != null) {
                String[] col = line.split("\t");
                String name = col[1];
                Double latitude = Double.parseDouble(col[4]);
                Double longitude = Double.parseDouble(col[5]);
                City city = new City(name, latitude, longitude);
                cities.add(city);
            }
            System.out.println("Read File Done");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cities;
    }
}
