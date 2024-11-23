package com.shirloin.suggestion_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.shirloin.suggestion_api.model.CitySuggestion;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

@SpringBootTest
@AutoConfigureMockMvc
class SuggestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final List<CitySuggestion> expectedSuggestions = Arrays.asList(
            new CitySuggestion("London", 42.98339, -81.23304, 0.8),
            new CitySuggestion("London", 39.88645, -83.44825, 0.6),
            new CitySuggestion("London", 37.12898, -84.08326, 0.5),
            new CitySuggestion("Londontowne", 38.93345, -76.54941, 0.4),
            new CitySuggestion("Londonderry", 42.86509, -71.37395, 0.4));

    @Test
    void testGetSuggestions() throws Exception {
        var resultActions = this.mockMvc.perform(get("/suggestions")
                .param("q", "Londo")
                .param("latitude", "43.70011")
                .param("longitude", "-79.4163"))
                .andExpect(status().isOk());
        for (int i = 0; i < expectedSuggestions.size(); i++) {
            CitySuggestion expected = expectedSuggestions.get(i);
            resultActions
                    .andExpect(jsonPath("$.suggestions[" + i + "].name").value(expected.getName()))
                    .andExpect(jsonPath("$.suggestions[" + i + "].latitude").value(expected.getLatitude()))
                    .andExpect(jsonPath("$.suggestions[" + i + "].longitude").value(expected.getLongitude()))
                    .andExpect(jsonPath("$.suggestions[" + i + "].score").value(expected.getScore()));
        }
    }
}