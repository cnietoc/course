package es.cnieto.servlet.html;

import org.junit.jupiter.api.Test;

import static es.cnieto.servlet.html.StubHttpServletRequest.createHttpServletRequestWithParameter;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TitleInputTest {
    private static final String EXPECTED_TITLE = "Nombre";
    private TitleInput titleInput = new TitleInput();

    @Test
    void getHtmlAsExpected() {
        assertEquals("<label>Nombre: <input type=\"text\" name=\"title\" /></label>", titleInput.getHtml());
    }

    @Test
    void getValueFromAsExpected() {
        String title = titleInput.getValueFrom(createHttpServletRequestWithParameter("title", EXPECTED_TITLE));

        assertEquals(EXPECTED_TITLE, title);
    }
}