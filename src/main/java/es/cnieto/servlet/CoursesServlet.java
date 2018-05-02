package es.cnieto.servlet;

import es.cnieto.AppContext;
import es.cnieto.domain.*;
import es.cnieto.servlet.html.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CoursesServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html";
    private CoursesListBox coursesListBox;
    private CreateCourseBox createCourseBox;
    private CoursesCreationService coursesCreationService;

    @Override
    public void init() {
        this.coursesCreationService = AppContext.getCoursesCreationService();
        this.coursesListBox = new CoursesListBox(AppContext.getCoursesReadService());
        this.createCourseBox = new CreateCourseBox(AppContext.getCourseLevelsReadService());
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
                    createCourseBox.getCourseTitleFrom(request),
                    createCourseBox.getCourseActiveFrom(request),
                    createCourseBox.getCourseHoursFrom(request),
                    createCourseBox.getCourseLevelFrom(request));
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
        out.println("<html>");
        out.println("<head>");
        out.println("<style>");
        out.print(FileReader.getFile("style.css"));
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println(coursesListBox.getHtml());
        out.println(createCourseBox.getHtml(errorMessage));
        out.println("</body>");
        out.println("</html>");
    }
}