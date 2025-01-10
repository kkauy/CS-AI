package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SurveyManagerTest {
    private SurveyManager surveyManager;

    @BeforeEach
    void setUp() {
        surveyManager = new SurveyManager(); // Initialize a new SurveyManager before each test.
    }

    @Test
    void testInitialization() {
        // Ensure the lists are initialized and empty.
        assertTrue(surveyManager.republican.isEmpty());
        assertTrue(surveyManager.democrat.isEmpty());
        assertTrue(surveyManager.green.isEmpty());
        assertTrue(surveyManager.libertarian.isEmpty());
    }

    @Test
    void testDeterminePredictedParty() {
        // Add mock data to simulate survey results.
        surveyManager.republican.add(1);
        surveyManager.republican.add(2);
        surveyManager.democrat.add(3);

        // Test the prediction logic.
        String predictedParty = surveyManager.determinePredictedParty();
        assertEquals("Republican", predictedParty, "The predicted party should be Republican.");
    }

    @Test
    void testSaveSurveyResults() throws Exception {

        surveyManager.republican.add(1);
        surveyManager.democrat.add(2);


        String fileName = "testSurveyResults.json";
        surveyManager.saveSurveyResults(fileName);


        File file = new File(fileName);
        assertTrue(file.exists(), "The survey results file should be created.");

        String content = Files.readString(file.toPath());
        assertTrue(content.contains("republican"), "The file should contain data for Republican.");
        assertTrue(content.contains("democrat"), "The file should contain data for Democrat.");

        // Clean up test file.
        file.delete();
    }

    @Test
    void testShowSelections() {
        surveyManager.republican.add(1);
        surveyManager.democrat.add(2);


        surveyManager.showSelections();
    }

    @Test
    void testInvalidInputHandling() {

    }
}
