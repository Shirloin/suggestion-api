package com.shirloin.suggestion_api.model;

public class CitySuggestion extends City {
    private Double score;

    public CitySuggestion(String name, Double latitude, Double longitude, Double score) {
        super(name, latitude, longitude);
        this.score = score;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

}