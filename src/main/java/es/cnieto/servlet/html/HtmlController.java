package es.cnieto.servlet.html;

import es.cnieto.domain.CourseLevelsReadService;
import es.cnieto.domain.CourseValidationException;
import es.cnieto.domain.CoursesCreationService;
import es.cnieto.domain.CoursesReadService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

public class HtmlController implements Serializable {
    private static final String CONTENT_TYPE = "text/html";
    private final CoursesListBox coursesListBox;
    private final CreateCourseBox createCourseBox;
    private final CoursesCreationService coursesCreationService;

    public HtmlController(CoursesListBox coursesListBox, CreateCourseBox createCourseBox, CoursesCreationService coursesCreationService) {
        this.coursesListBox = coursesListBox;
        this.createCourseBox = createCourseBox;
        this.coursesCreationService = coursesCreationService;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        printHtml(response, null);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String errorMessage = null;
        try {
            coursesCreationService.create(
                    createCourseBox.getCourseTitleFrom(request),
                    createCourseBox.getCourseActiveFrom(request),
                    createCourseBox.getCourseHoursFrom(request),
                    createCourseBox.getCourseLevelFrom(request),
                    createCourseBox.getCourseTeacherFrom(request).orElse(null));
            response.setStatus(201);
        } catch (CourseValidationException e) {
            errorMessage = e.getMessage();
            response.setStatus(400);
        }
        printHtml(response, errorMessage);
    }

    private void printHtml(HttpServletResponse response, String errorMessage) throws IOException {
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