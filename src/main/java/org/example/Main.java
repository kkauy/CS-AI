package org.example;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws Exception {
        final SurveyManager surveyManager = new SurveyManager();

        surveyManager.conductSurvey();
        surveyManager.showSelections();
        surveyManager.showAffiliatedParty();
        surveyManager.saveSurveyResults("surveyResults.json");

       // save survey results to a file
        String fileName = "output/survey_results.txt";
        surveyManager.saveSurveyResults(fileName);

    }
}