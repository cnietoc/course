package es.cnieto.servlet.html;

import javax.servlet.http.HttpServletRequest;

public class HoursInput {
    private static final String PARAMETER_NAME = "hours";
    private static final String INPUT_LABEL_TEXT = "Horas: ";

    String getHtml() {
        return "<label>" + INPUT_LABEL_TEXT + "<input type=\"text\" name=\"" + PARAMETER_NAME + "\" /></label>";
    }

    Integer getValueFrom(HttpServletRequest request) {
        try {
            return Integer.parseInt(request.getParameter("hours"));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
