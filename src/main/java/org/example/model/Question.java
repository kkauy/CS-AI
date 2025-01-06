package org.example.model;

import java.util.List;
import java.util.Map;

public class Question {

    private int id;
    private String title;
    private Map<String, String> choices;
    private Map<String, List<String>> answers;

    public Question() {
    }

    public Question(int id, String title, Map<String, String> choices) {
        this.id = id;
        this.title = title;
        this.choices = choices;
    }

    public Question(int id, String title, Map<String, String> choices, Map<String, List<String>> answers) {
        this.id = id;
        this.title = title;
        this.choices = choices;
        this.answers = answers;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, String> getChoices() {
        return choices;
    }

    public void setChoices(Map<String, String> choices) {
        this.choices = choices;
    }

    public Map<String, List<String>> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<String, List<String>> answers) {
        this.answers = answers;
    }

    // Override toString method for better display of object
    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", choices=" + choices +
                '}';
    }
}

