package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.helper.QuestionReader;
import org.example.model.Question;

import java.io.File;
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
                    switch (answer) {
                        case "A" ->  {
                            republican.add(question.getId());
                        }

                        case "B" -> {
                                democrat.add(question.getId());

                        }
                        case "C" ->
                                {
                                    green.add(question.getId());

                                }
                        case "D" -> {
                            libertarian.add(question.getId());

                        }
                        default -> {
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
    }

    public void showSelections() {
        System.out.println("Republican Choices: " + Arrays.toString(republican.toArray()));
        System.out.println("Democrat Choices: " + Arrays.toString(democrat.toArray()));
        System.out.println("Green Choices: " + Arrays.toString(green.toArray()));
        System.out.println("Liberal Choices: " + Arrays.toString(libertarian.toArray()));
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


    public void saveSurveyResults(String fileName)  throws  Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> surveyData = new HashMap<>();
        surveyData.put("republican", republican);
        surveyData.put("democrat", democrat);
        surveyData.put("green", green);
        surveyData.put("libertarian", libertarian);
        surveyData.put("timestamp", LocalDateTime.now().toString());
        surveyData.put("userId", UUID.randomUUID().toString());
        mapper.writeValue(new File(fileName), surveyData);
    }
}
