package org.example.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Question;

import java.io.InputStream;

public class QuestionReader {

    public Question[] getQuestionsFromJson() throws Exception {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("questions.json");
            return objectMapper.readValue(inputStream, Question[].class);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
