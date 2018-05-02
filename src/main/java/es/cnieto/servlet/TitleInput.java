package es.cnieto.servlet;

import javax.servlet.http.HttpServletRequest;

class TitleInput {
    private static final String PARAMETER_NAME = "title";

    String getHtml() {
        return "<label>Nombre: <input type=\"text\" name=\"" + PARAMETER_NAME + "\" /></label>";
    }

    String getValueFrom(HttpServletRequest request) {
        return request.getParameter(PARAMETER_NAME);
    }
}
