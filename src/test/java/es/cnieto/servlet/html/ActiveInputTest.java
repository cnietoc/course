package es.cnieto.servlet.html;

import org.junit.jupiter.api.Test;

import static es.cnieto.servlet.html.StubHttpServletRequest.createHttpServletRequestWithParameter;
import static org.junit.jupiter.api.Assertions.*;

class ActiveInputTest {
    private static final String PARAMETER_NAME = "active";
    private static final String PARAMETER_TRUE_VALUE = "yes";
    private static final String RANDOM_STRING = "asdfasdf";
    private ActiveInput activeInput = new ActiveInput();

    @Test
    void getHtmlAsExpected() {
        assertEquals("<label>Activo: <input type=\"checkbox\" name=\"active\" value=\"yes\"/></label>", activeInput.getHtml());
    }

    @Test
    void getValueFromWhenIsValid() {
        Boolean active = activeInput.getValueFrom(createHttpServletRequestWithParameter(PARAMETER_NAME, PARAMETER_TRUE_VALUE));

        assertTrue(active);
    }

    @Test
    void getValueFromWithAString() {
        Boolean active = activeInput.getValueFrom(createHttpServletRequestWithParameter(PARAMETER_NAME, RANDOM_STRING));

        assertFalse(active);
    }

    @Test
    void getValueFromWithAnEmptyString() {
        Boolean active = activeInput.getValueFrom(createHttpServletRequestWithParameter(PARAMETER_NAME, ""));

        assertFalse(active);
    }

    @Test
    void getValueFromWithAnNull() {
        Boolean active = activeInput.getValueFrom(createHttpServletRequestWithParameter(PARAMETER_NAME, null));

        assertFalse(active);
    }
}