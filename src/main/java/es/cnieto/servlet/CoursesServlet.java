package es.cnieto.servlet;

import es.cnieto.database.CoursesDAO;
import es.cnieto.database.CoursesJDBCRepository;
import es.cnieto.database.DatabaseManager;
import es.cnieto.domain.Course;
import es.cnieto.domain.CoursesCreationService;
import es.cnieto.domain.CoursesReadService;
import es.cnieto.domain.CoursesRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

public class CoursesServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html";
    private static final String DATABASE_PATH = "coursesDB";
    private CoursesReadService coursesReadService;
    private CoursesCreationService coursesCreationService;

    @Override
    public void init() throws ServletException {
        try {
            DatabaseManager databaseManager = new DatabaseManager(DATABASE_PATH);
            CoursesDAO coursesDAO = new CoursesDAO(databaseManager);
            CoursesRepository coursesRepository = new CoursesJDBCRepository(coursesDAO);
            this.coursesReadService = new CoursesReadService(coursesRepository);
            this.coursesCreationService = new CoursesCreationService(coursesRepository);
        } catch (SQLException e) {
            throw new ServletException("Error initializing database", e);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        printHtmlPage(response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        coursesCreationService.create(getTitleFrom(request), getActiveFrom(request), getHoursFrom(request));
        printHtmlPage(response);
    }

    // TODO move to a collaborator and test it
    private String getTitleFrom(HttpServletRequest request) {
        return request.getParameter("title");
    }

    // TODO move to a collaborator and test it
    private Boolean getActiveFrom(HttpServletRequest request) {
        return "yes".equals(request.getParameter("active"));
    }

    // TODO move to a collaborator and test it
    private Integer getHoursFrom(HttpServletRequest request) {
        try {
            return Integer.parseInt(request.getParameter("hours"));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void printHtmlPage(HttpServletResponse response) throws IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        appendCoursesList(out);

        appendNewCourseBox(out);
    }

    private void appendNewCourseBox(PrintWriter out) {
        out.println("<div>");
        out.println("<h1>A&ntilde;adir nuevo curso</h1>");
        out.println("<form method=\"post\">");
        out.println("<label>Nombre: <input type=\"text\" name=\"title\" /></label>");
        out.println("<label>Horas: <input type=\"text\" name=\"hours\" /></label>");
        out.println("<label>Activo: <input type=\"checkbox\" name=\"active\" value=\"yes\"/></label>");
        out.println("<input type=\"submit\" value=\"Enviar\" />");
        out.println("</form>");
        out.println("</div>");
    }

    private void appendCoursesList(PrintWriter out) {
        out.println("<div>");

        out.println("<h1>Cursos</h1>");

        List<Course> courses = coursesReadService.readActivesOrderedByTitle();
        for (Course course : courses) {
            out.println("<div>");
            out.println("<div>" + course.getTitle() + "</div>");
            out.println("<div>" + course.getHours() + "</div>");
            out.println("</div>");
        }
        out.println("</div>");
    }
}