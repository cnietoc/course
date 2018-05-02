package es.cnieto.servlet;

import es.cnieto.AppContext;
import es.cnieto.domain.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CoursesServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html";
    private TitleInput titleInput;
    private HoursInput hoursInput;
    private ActiveInput activeInput;
    private LevelInput levelInput;
    private CoursesReadService coursesReadService;
    private CoursesCreationService coursesCreationService;
    private CourseLevelsReadService courseLevelsReadService;

    @Override
    public void init() {
        this.coursesReadService = AppContext.getCoursesReadService();
        this.coursesCreationService = AppContext.getCoursesCreationService();
        this.courseLevelsReadService = AppContext.getCourseLevelsReadService();
        this.titleInput = new TitleInput();
        this.hoursInput = new HoursInput();
        this.activeInput = new ActiveInput();
        this.levelInput = new LevelInput(courseLevelsReadService);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        printHtmlPage(response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String errorMessage = null;
        try {
            coursesCreationService.create(
                    titleInput.getValueFrom(request),
                    activeInput.getValueFrom(request),
                    hoursInput.getValueFrom(request),
                    levelInput.getValueFrom(request));
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
        out.println("<div style=\"display: table\" class=\"courseTable\">");
        out.println("<div style=\"display: table-heading\" class=\"courseTableRow\">");
        out.println("<div style=\"display: table-cell\" class=\"courseTableCell\">Título</div>");
        out.println("<div style=\"display: table-cell\" class=\"courseTableCell\">Horas</div>");
        out.println("<div style=\"display: table-cell\" class=\"courseTableCell\">Nivel</div>");
        out.println("</div>");

        List<Course> courses = coursesReadService.readActivesOrderedByTitle();
        for (Course course : courses) {
            out.println("<div style=\"display: table-row\" class=\"courseTableRow\">");
            out.println("<div style=\"display: table-cell\" class=\"courseTableCell\">" + course.getTitle() + "</div>");
            out.println("<div style=\"display: table-cell\" class=\"courseTableCell\">" + course.getHours() + "</div>");
            out.println("<div style=\"display: table-cell\" class=\"courseTableCell\">" + course.getLevel() + "</div>");
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
        out.println(levelInput.getHtml());
        out.println("<input type=\"submit\" value=\"Enviar\" />");
        out.println("</form>");
        out.println("</div>");
    }
}