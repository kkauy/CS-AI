package org.example.data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ResponseSaver {

    public void saveResponses(String predictedParty, List<String> responses, String actualParty) {
        String fileName = predictedParty.toLowerCase() + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            // Write metadata (Predicted and Actual Party)
            writer.write("Predicted Party: " + predictedParty);
            writer.newLine();
            writer.write("Declared Party: " + actualParty);
            writer.newLine();

            // Write the user's responses
            writer.write("Responses: " + String.join(", ", responses));
            writer.newLine();
            writer.write("--------------------------");
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

