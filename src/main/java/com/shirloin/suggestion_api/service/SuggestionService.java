package com.shirloin.suggestion_api.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<CitySuggestion> getSuggestions(String query, Double latitude, Double longitude) {
        // List<City> filteredCities = cities.stream()
        // .filter(city -> city.getName().toLowerCase().contains(query.toLowerCase()))
        // .collect(Collectors.toList());
        // for (City city : filteredCities) {
        // System.out.println(
        // this.scoreCalculator.calculateLocationScore(city.getLatitude(),
        // city.getLongitude(), latitude,
        // longitude));
        // }
        // List<CitySuggestion> suggestionsWithScores = filteredCities.stream()
        // .map(city -> new CitySuggestion(city.getName(), city.getLatitude(),
        // city.getLongitude(),
        // this.scoreCalculator.calculateLocationScore(city.getLatitude(),
        // city.getLongitude(), latitude,
        // longitude)))
        // .collect(Collectors.toList());

        // return suggestionsWithScores.stream()
        // .filter(suggestion -> suggestion.getScore() > 0)
        // .sorted(Comparator.comparingDouble(CitySuggestion::getScore).reversed())
        // .collect(Collectors.toList());

        return cities.stream()
                .filter(city -> city.getName().toLowerCase().contains(query.toLowerCase()))
                .map(city -> new CitySuggestion(city.getName(), city.getLatitude(),
                        city.getLongitude(),
                        this.scoreCalculator.calculateLocationScore(city.getLatitude(),
                                city.getLongitude(), latitude,
                                longitude)))
                .filter(suggestion -> suggestion.getScore() >= 0)
                .sorted(Comparator.comparingDouble(CitySuggestion::getScore).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }
}