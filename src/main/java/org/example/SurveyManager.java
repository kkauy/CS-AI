package org.example;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.example.helper.QuestionReader;
import org.example.model.Question;

import java.time.LocalDateTime;
import java.util.*;

public class SurveyManager {
    final List<Integer> republican;
    final List<Integer> democrat;
    final List<Integer> green;
    final List<Integer> libertarian;

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
            final Set<String> validChoices = new HashSet<>(Set.of("A", "B", "C", "D"));
            for (final Question question : questions) {
                System.out.println(question.getTitle());
                final Map<String, String> choice = question.getChoices();
                for (final Map.Entry<String, String> entry : choice.entrySet()) {
                    System.out.println(entry.getKey() + ": " + entry.getValue());
                }

                int attempts = 0;

                while (attempts < 4 ) {
                    String answer = scanner.nextLine().trim().toUpperCase();

                    // check answer is null
                    if (answer.isEmpty() || validChoices.contains(answer)) {
                        attempts++;
                        if (attempts < 4) {
                            System.out.println("Invalid input. You have " + (4 - attempts) + " attempts remaining. Please enter A, B, C, or D.");
                        } else {
                            System.out.println("You have exceeded the maximum attempts. Moving on to the next question.");
                        }
                        continue;
                    }

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

    public void saveSurveyResults(String fileName) throws IOException {
        saveToFile("data/republican.txt", republican);
        saveToFile("data/democrat.txt", democrat);
        saveToFile("data/green.txt", green);
        saveToFile("data/libertarian.txt", libertarian);
    }

    private void saveToFile(String fileName, List<Integer> data) throws IOException {
        File file = new File(fileName);

        // Ensure parent directory exists
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        // Write data to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("Survey Results: ");
            writer.newLine();
            writer.write("--------");
            writer.newLine();

            if (data.isEmpty()) {
                writer.write("No results available.");
            } else {
                for (Integer entry : data) {
                    writer.write(entry);
                    writer.newLine();
                }
            }
        }
        System.out.println("Results written to " + fileName);
    }




    private void writeResponsesToFile(String fileName, List<Integer> responses) throws Exception {

        File dataFolder = new File("data");
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }


        File file = new File(dataFolder, fileName);


        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Integer response : responses) {
                writer.write(response + System.lineSeparator());
            }
            System.out.println("Responses saved to " + file.getAbsolutePath());
        }
    }

    // write to each file
    public void saveResponsesByParty() throws Exception {
        writeResponsesToFile("republican_responses.txt", republican);
        writeResponsesToFile("democrat_responses.txt", democrat);
        writeResponsesToFile("green_responses.txt", green);
        writeResponsesToFile("libertarian_responses.txt", libertarian);
    }


    public void showAffiliatedParty() {
        String affiliatedParty = determinePredictedParty();
        System.out.println("Based on your responses, your affiliated party is: " + affiliatedParty);
    }

}
