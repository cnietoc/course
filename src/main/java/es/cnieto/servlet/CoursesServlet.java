package es.cnieto.servlet;

import es.cnieto.AppContext;
import es.cnieto.servlet.html.*;
import es.cnieto.servlet.rest.RestCourseController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CoursesServlet extends HttpServlet {
    private HtmlController htmlController;
    private RestCourseController restCourseController;

    @Override
    public void init() {
        this.htmlController = new HtmlController(AppContext.getCoursesReadService(), AppContext.getCourseLevelsReadService(), AppContext.getCoursesCreationService());
        this.restCourseController = new RestCourseController(AppContext.getCoursesReadService(), AppContext.getCoursesCreationService());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (isRestCourse(request)) {
            restCourseController.doGet(request, response);
        } else {
            htmlController.doGet(request, response);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (isRestCourse(request)) {
            restCourseController.doPost(request, response);
        } else {
            htmlController.doPost(request, response);
        }

    }

    private boolean isRestCourse(HttpServletRequest request) {
        return "/rest/course/".equals(request.getRequestURI());
    }
}