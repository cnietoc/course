package es.cnieto.servlet.html;

import java.io.InputStream;
import java.util.Scanner;

class FileReader {
    static String getFile(String fileName) {

        StringBuilder result = new StringBuilder();

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        try (Scanner scanner = new Scanner(inputStream)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }
        }

        return result.toString();

    }
}
