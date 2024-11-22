package com.shirloin.suggestion_api.util;

import org.springframework.stereotype.Component;

@Component
public class ScoreCalculator {
    private final double AVERAGE_RADIUS_OF_EARCH_KM = 6371;

    public Double getDistanceFromLatLonInKm(Double latitude1, Double longitude1, Double latitude2, Double longitude2) {
        Double latDistance = deg2rad(latitude2 - latitude1);
        Double longDistance = deg2rad(longitude2 - longitude1);

        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(latitude2)) * Math.cos(Math.toRadians(latitude1))
                        * Math.sin(longDistance / 2) * Math.sin(longDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        Double d = this.AVERAGE_RADIUS_OF_EARCH_KM * c;
        return d;
    }

    public Double deg2rad(Double deg) {
        return deg * (Math.PI / 180);
    }

    public Double calculateLocationScore(Double latitude1, Double longitude1, Double latitude2, Double longitude2) {
        Double latitude = Math.abs(latitude2 - latitude1);
        Double longitude = Math.abs(longitude2 - longitude1);
        Double score = 10 - (latitude + longitude) / 2;
        // System.out.println("Score: " + Math.round(score) / 10.0);
        score = score > 0 ? (Math.round(score) / 10.0) : 0.0;
        return score;
    }
}