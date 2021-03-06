package es.cnieto.servlet.html;

import javax.servlet.http.HttpServletRequest;

public class ActiveInput {
    private static final String PARAMETER_NAME = "active";
    private static final String PARAMETER_TRUE_VALUE = "yes";
    private static final String INPUT_LABEL_TEXT = "Activo: ";

    String getHtml() {
        return "<label>" + INPUT_LABEL_TEXT + "<input type=\"checkbox\" name=\"" + PARAMETER_NAME + "\" value=\"" + PARAMETER_TRUE_VALUE + "\"/></label>";
    }

    Boolean getValueFrom(HttpServletRequest request) {
        return PARAMETER_TRUE_VALUE.equals(request.getParameter(PARAMETER_NAME));
    }
}
