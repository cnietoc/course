package es.cnieto.servlet.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import es.cnieto.domain.Course;
import es.cnieto.domain.CourseValidationException;
import es.cnieto.domain.CoursesCreationService;
import es.cnieto.domain.CoursesReadService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class RestCourseController {
    private final CoursesReadService coursesReadService;
    private final CourseRestConverter courseRestConverter;
    private final CoursesCreationService coursesCreationService;
    private final Gson gson;

    public RestCourseController(CoursesReadService coursesReadService, CoursesCreationService coursesCreationService) {
        this.coursesReadService = coursesReadService;
        this.coursesCreationService = coursesCreationService;
        this.courseRestConverter = new CourseRestConverter();

        GsonBuilder gsonBuilder = new GsonBuilder();
        this.gson = gsonBuilder.create();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Course> courses = coursesReadService.readActivesOrderByTitle();

        List<CourseRest> coursesRest = courses.stream().map(courseRestConverter::from).collect(toList());

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        gson.toJson(coursesRest, out);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            CourseRest courseRest = gson.fromJson(request.getReader(), CourseRest.class);
            coursesCreationService.create(
                    courseRest.getTitle(),
                    courseRest.getActive(),
                    courseRest.getHours(),
                    courseRest.getLevelId());
            response.setStatus(201);
        } catch (CourseValidationException e) {
            PrintWriter out = response.getWriter();
            out.print(e.getMessage());
            response.setStatus(400);
        }

    }
}
