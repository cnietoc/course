package es.cnieto.servlet;

import javax.servlet.http.HttpServletRequest;

class ActiveInput {
    private static final String PARAMETER_NAME = "active";
    private static final String PARAMETER_TRUE_VALUE = "yes";

    String getHtml() {
        return "<label>Activo: <input type=\"checkbox\" name=\"" + PARAMETER_NAME + "\" value=\"" + PARAMETER_TRUE_VALUE + "\"/></label>";
    }

    Boolean getValueFrom(HttpServletRequest request) {
        return PARAMETER_TRUE_VALUE.equals(request.getParameter(PARAMETER_NAME));
    }
}
