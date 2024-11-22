package com.shirloin.suggestion_api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shirloin.suggestion_api.model.CitySuggestion;
import com.shirloin.suggestion_api.service.SuggestionService;

@RestController
@RequestMapping("/suggestions")
public class SuggestionController {
    private final SuggestionService suggestionService;

    public SuggestionController(SuggestionService suggestionService) {
        this.suggestionService = suggestionService;
    }

    @GetMapping
    public ResponseEntity<Map<String, List<CitySuggestion>>> getSuggestions(
            @RequestParam String q,
            @RequestParam(required = false) Double latitude,
            @RequestParam(required = false) Double longitude) {
        List<CitySuggestion> suggestions = suggestionService.getSuggestions(q, latitude, longitude);
        Map<String, List<CitySuggestion>> response = new HashMap<>();
        response.put("suggestions", suggestions);
        return ResponseEntity.ok(response);
    }
}