package es.cnieto;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CoursesServlet extends HttpServlet {

    private static final String CONTENT_TYPE = "text/html";

    public void init() {
        // do nothing
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType(CONTENT_TYPE);

        PrintWriter out = response.getWriter();
        out.println("<h1>" + "hola mundo" + "</h1>");
    }

    public void destroy() {
        // do nothing.
    }
}