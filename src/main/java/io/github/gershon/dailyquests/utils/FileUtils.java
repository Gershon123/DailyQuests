package io.github.gershon.dailyquests.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

    public static void writeToFile(String directory, String filename, String json) {
        try {
            File fileDirectory = new File(directory);

            if (!fileDirectory.exists()) {
                fileDirectory.mkdir();
            }

            File questFile = new File(directory + filename);
            questFile.createNewFile();
            FileWriter writer = new FileWriter(directory + filename);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
