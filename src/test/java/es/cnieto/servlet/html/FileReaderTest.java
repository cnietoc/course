package es.cnieto.servlet.html;

import es.cnieto.servlet.html.FileReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileReaderTest {
    @Test
    void readFileAsExpected() {
        String style = FileReader.getFile("style.css");

        assertNotNull(style);
    }
}