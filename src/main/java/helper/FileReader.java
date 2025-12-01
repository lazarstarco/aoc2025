package main.java.helper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileReader {

    public static List<String> readForClass(Class<?> callerClass) {
        try {
            String folder = callerClass.getPackageName()
                    .substring(callerClass.getPackageName().lastIndexOf('.') + 1);

            Path resourcePath = Path.of("src", "main", "resources", folder, "input.txt");

            return Files.readAllLines(resourcePath);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
