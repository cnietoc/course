package es.cnieto.servlet;

import es.cnieto.database.CoursesDAO;
import es.cnieto.database.CoursesJDBCRepository;
import es.cnieto.database.DatabaseManager;
import es.cnieto.domain.*;

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
    private final TitleInput titleInput = new TitleInput();
    private final HoursInput hoursInput = new HoursInput();
    private final ActiveInput activeInput = new ActiveInput();
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
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String errorMessage = null;
        try {
            coursesCreationService.create(titleInput.getValueFrom(request), activeInput.getValueFrom(request), hoursInput.getValueFrom(request));
        } catch (CourseValidationException e) {
            errorMessage = e.getMessage();
        }
        printHtmlPage(response, errorMessage);
    }

    private void printHtmlPage(HttpServletResponse response) throws IOException {
        printHtmlPage(response, null);
    }

    private void printHtmlPage(HttpServletResponse response, String errorMessage) throws IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        appendCoursesList(out);
        if (errorMessage != null)
            appendErrorMessage(out, errorMessage);
        appendNewCourseBox(out);
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

    private void appendErrorMessage(PrintWriter out, String errorMessage) {
        out.println("<div>");
        out.println("<h2>Error: " + errorMessage + "</h1>");
        out.println("</div>");
    }

    private void appendNewCourseBox(PrintWriter out) {
        out.println("<div>");
        out.println("<h1>A&ntilde;adir nuevo curso</h1>");
        out.println("<form method=\"post\">");
        out.println(titleInput.getHtml());
        out.println(hoursInput.getHtml());
        out.println(activeInput.getHtml());
        out.println("<input type=\"submit\" value=\"Enviar\" />");
        out.println("</form>");
        out.println("</div>");
    }
}