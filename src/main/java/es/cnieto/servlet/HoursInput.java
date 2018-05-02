package es.cnieto.servlet;

import javax.servlet.http.HttpServletRequest;

class HoursInput {
    private static final String PARAMETER_NAME = "hours";

    String getHtml() {
        return "<label>Horas: <input type=\"text\" name=\"" + PARAMETER_NAME + "\" /></label>";
    }

    Integer getValueFrom(HttpServletRequest request) {
        try {
            return Integer.parseInt(request.getParameter("hours"));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
