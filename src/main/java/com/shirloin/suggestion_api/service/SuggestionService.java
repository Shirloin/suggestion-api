package com.shirloin.suggestion_api.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.shirloin.suggestion_api.model.City;
import com.shirloin.suggestion_api.model.CitySuggestion;
import com.shirloin.suggestion_api.util.DataLoader;
import com.shirloin.suggestion_api.util.ScoreCalculator;

import jakarta.annotation.PostConstruct;

@Service
public class SuggestionService {
    private final List<City> cities = new ArrayList<>();
    private final DataLoader dataLoader;
    private final ScoreCalculator scoreCalculator;

    public SuggestionService(DataLoader dataLoader, ScoreCalculator scoreCalculator) {
        this.dataLoader = dataLoader;
        this.scoreCalculator = scoreCalculator;
    }

    @PostConstruct
    public void loadCities() {
        cities.addAll(dataLoader.loadCitiesFromTSV());
    }

    @Cacheable(value = "citySuggestions", key = "#query + '-' + #latitude + '-' + #longitude")
    public List<CitySuggestion> getSuggestions(String query, Double latitude, Double longitude) {

        return cities.stream()
                .filter(city -> city.getName().toLowerCase().startsWith(query.toLowerCase()))
                .map(city -> {
                    Double score = 0.0;
                    if (latitude != null && longitude != null) {
                        score = this.scoreCalculator.calculateLocationScore(city.getLatitude(), city.getLongitude(),
                                latitude, longitude, query, city.getName());
                    }

                    return new CitySuggestion(city.getName(), city.getLatitude(),
                            city.getLongitude(), score);
                })
                .filter(suggestion -> suggestion.getScore() > 0)
                .sorted(Comparator.comparingDouble(CitySuggestion::getScore).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }
}