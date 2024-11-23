package com.shirloin.suggestion_api.util;

import org.springframework.stereotype.Component;

@Component
public class ScoreCalculator {
    private final Double AVERAGE_RADIUS_OF_EARCH_KM = 6371.0;

    public Double getDistanceFromLatLonInKm(Double latitude1, Double longitude1, Double latitude2, Double longitude2) {
        Double latDistance = deg2rad(latitude2 - latitude1);
        Double longDistance = deg2rad(longitude2 - longitude1);

        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(deg2rad(latitude1)) * Math.cos(deg2rad(latitude2))
                        * Math.sin(longDistance / 2) * Math.sin(longDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        Double d = this.AVERAGE_RADIUS_OF_EARCH_KM * c;
        return d;
    }

    public Double deg2rad(Double deg) {
        return deg * (Math.PI / 180);
    }

    public int levenshtein(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    int cost = s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1;
                    dp[i][j] = Math.min(
                            Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                            dp[i - 1][j - 1] + cost);
                }
            }
        }
        return dp[s1.length()][s2.length()];
    }

    public Double calculateNameSimilarity(String query, String cityName) {
        int levenshteinDistance = levenshtein(query.toLowerCase(), cityName.toLowerCase());
        int maxLen = Math.max(query.length(), cityName.length());

        if (maxLen == 0)
            return 1.0;

        return 1.0 - ((double) levenshteinDistance / maxLen);
    }

    public Double calculateLocationScore(Double latitude1, Double longitude1, Double latitude2, Double longitude2,
            String query, String cityName) {
        Double distanceKm = getDistanceFromLatLonInKm(latitude1, longitude1, latitude2, longitude2);
        Double distanceScore = Math.max(0, 1 - (distanceKm / 1000));

        Double nameSimilarityScore = calculateNameSimilarity(query, cityName);
        Double finalScore = (0.5 * distanceScore) + (0.5 * nameSimilarityScore);

        System.out.println("Distance " + cityName + ": " + distanceScore);
        System.out.println("Distance " + cityName + ": " + nameSimilarityScore);
        System.out.println("Distance " + cityName + ": " + finalScore);
        System.out.println("");
        return Math.round(finalScore * 10) / 10.0;
    }
}