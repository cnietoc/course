package es.cnieto.servlet.html;

import javax.servlet.http.HttpServletRequest;

public class TitleInput {
    private static final String PARAMETER_NAME = "title";
    private static final String INPUT_LABEL_TEXT = "Nombre: ";

    String getHtml() {
        return "<label>" + INPUT_LABEL_TEXT + "<input type=\"text\" name=\"" + PARAMETER_NAME + "\" /></label>";
    }

    String getValueFrom(HttpServletRequest request) {
        return request.getParameter(PARAMETER_NAME);
    }
}
