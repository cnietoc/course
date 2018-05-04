package es.cnieto.servlet.html;

import es.cnieto.domain.Course;
import es.cnieto.domain.CoursesReadService;

import java.io.Serializable;
import java.util.List;

public class CoursesListBox implements Serializable {
    private final CoursesReadService coursesReadService;

    public CoursesListBox(CoursesReadService coursesReadService) {
        this.coursesReadService = coursesReadService;
    }

    public String getHtml() {
        StringBuilder html = new StringBuilder();
        html.append("<div>");
        html.append("<h1>Cursos</h1>");
        html.append("<div class=\"courseTable\">");
        html.append("<div class=\"courseTableHeader\">");
        html.append("<div class=\"courseTableCell\">Título</div>");
        html.append("<div class=\"courseTableCell\">Horas</div>");
        html.append("<div class=\"courseTableCell\">Nivel</div>");
        html.append("</div>");

        List<Course> courses = coursesReadService.readActivesOrderByTitle();
        for (Course course : courses) {
            html.append("<div class=\"courseTableRow\">");
            html.append("<div class=\"courseTableCell\">");
            html.append(course.getTitle());
            html.append("</div>");
            html.append("<div class=\"courseTableCell\">");
            html.append(course.getHours());
            html.append("</div>");
            html.append("<div class=\"courseTableCell\">");
            html.append(course.getLevel());
            html.append("</div>");
            html.append("</div>");
        }
        html.append("</div>");

        return html.toString();
    }
}