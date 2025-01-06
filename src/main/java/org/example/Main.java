package org.example;

public class Main {
    public static void main(String[] args) throws Exception {
        final SurveyManager surveyManager = new SurveyManager();
        surveyManager.conductSurvey();
        surveyManager.showSelections();
        surveyManager.saveSurveyResults("surveyResults.json");
        System.out.println(surveyManager.determinePredictedParty());
    }
}