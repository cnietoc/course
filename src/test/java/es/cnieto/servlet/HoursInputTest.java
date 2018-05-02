package es.cnieto.servlet;

import org.junit.jupiter.api.Test;

import static es.cnieto.servlet.StubHttpServletRequest.createHttpServletRequestWithParameter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class HoursInputTest {
    private static final Integer EXPECTED_VALID_HOURS = 324;
    private static final String INVALID_HOURS = "invalid number";
    private static final String PARAMETER_NAME = "hours";
    private HoursInput hoursInput = new HoursInput();

    @Test
    void getHtmlAsExpected() {
        assertEquals("<label>Horas: <input type=\"text\" name=\"hours\" /></label>", hoursInput.getHtml());
    }

    @Test
    void getValueFromWithANumber() {
        Integer hours = hoursInput.getValueFrom(createHttpServletRequestWithParameter(PARAMETER_NAME, EXPECTED_VALID_HOURS.toString()));

        assertEquals(EXPECTED_VALID_HOURS, hours);
    }

    @Test
    void getValueFromWithAString() {
        Integer hours = hoursInput.getValueFrom(createHttpServletRequestWithParameter(PARAMETER_NAME, INVALID_HOURS));

        assertNull(hours);
    }

    @Test
    void getValueFromWithAnEmptyString() {
        Integer hours = hoursInput.getValueFrom(createHttpServletRequestWithParameter(PARAMETER_NAME, ""));

        assertNull(hours);
    }

    @Test
    void getValueFromWithAnNull() {
        Integer hours = hoursInput.getValueFrom(createHttpServletRequestWithParameter(PARAMETER_NAME, null));

        assertNull(hours);
    }
}