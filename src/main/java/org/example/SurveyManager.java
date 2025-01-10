package org.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.example.helper.QuestionReader;
import org.example.model.Question;

import java.time.LocalDateTime;
import java.util.*;

public class SurveyManager {
    private final List<Integer> republican;
    private final List<Integer> democrat;
    private final List<Integer> green;
    private final List<Integer> libertarian;

    public SurveyManager() {
        this.republican = new ArrayList<>();
        this.democrat = new ArrayList<>();
        this.green = new ArrayList<>();
        this.libertarian = new ArrayList<>();
    }

    public void conductSurvey() throws Exception {
        try (final Scanner scanner = new Scanner(System.in)) {
            System.out.println("Welcome to the political survey!");
            final QuestionReader questionReader = new QuestionReader();
            final Question[] questions = questionReader.getQuestionsFromJson();
            for (final Question question : questions) {
                System.out.println(question.getTitle());
                final Map<String, String> choice = question.getChoices();
                for (final Map.Entry<String, String> entry : choice.entrySet()) {
                    System.out.println(entry.getKey() + ": " + entry.getValue());
                }

                int attempts = 0;

                while (attempts < 4 ) {
                    String answer = scanner.nextLine().trim().toUpperCase();
                    Map<String, List<String>> predictionAnswers = question.getAnswers();
                    List<String> parties = predictionAnswers.get(answer);

                    if (parties != null) {
                        for (String partie : parties) {
                            switch (partie.toLowerCase()) {
                                case "republican" -> republican.add(question.getId());
                                case "democrat" -> democrat.add(question.getId());
                                case "green" -> green.add(question.getId());
                                case "libertarian" -> libertarian.add(question.getId());
                            }
                        }
                        break;
                    } else {
                        attempts++;
                        if (attempts < 4) {
                            System.out.println("Invalid choice, please try again.");
                        } else {
                            System.out.println("You have exceeded the maximum attempts for this question.");
                        }
                 }
                }
            }
        }
    }

    public void showSelections() {
        System.out.println("----");
        System.out.println("Republican Choices: " + Arrays.toString(republican.toArray()));
        System.out.println("Democrat Choices: " + Arrays.toString(democrat.toArray()));
        System.out.println("Green Choices: " + Arrays.toString(green.toArray()));
        System.out.println("Liberal Choices: " + Arrays.toString(libertarian.toArray()));
        System.out.println("----");
    }

    public String determinePredictedParty() {
        int republicanScore = republican.size();
        int democratScore = democrat.size();
        int greenScore = green.size();
        int libertarianScore = libertarian.size();
        if (republicanScore >= democratScore && republicanScore >= greenScore && republicanScore >= libertarianScore) {
            return "Republican";
        } else if (democratScore >= republicanScore && democratScore >= greenScore && democratScore >= libertarianScore) {
            return "Democrat";
        } else if (greenScore >= republicanScore && greenScore >= democratScore && greenScore >= libertarianScore) {
            return "Green Party";
        } else {
            return "Libertarian";
        }
    }


    public void saveSurveyResults(String fileName)  throws  IOException {
        File file = new File(fileName);

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Survey Results: ");
            writer.newLine();
            writer.write("--------");
            writer.newLine();
            writer.write("Republican:" + republican);
            writer.newLine();
            writer.write("Democrat:" + democrat);
            writer.newLine();
            writer.write("Green:" + green);
            writer.newLine();
            writer.write("Libertarian:" + libertarian);
            writer.newLine();
        }
    }

    public void showAffiliatedParty() {
        System.out.println("----");
        String affiliatedParty = determinePredictedParty();
        System.out.println("Based on your responses, your affiliated party is: " + affiliatedParty);
        System.out.println("----");
    }

}
