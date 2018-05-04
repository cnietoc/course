package es.cnieto.servlet.html;

import es.cnieto.domain.Course;
import es.cnieto.domain.CoursesReadService;
import es.cnieto.domain.Teacher;

import java.io.Serializable;
import java.util.List;

public class CoursesListBox implements Serializable {
    private static final String LIST_TITLE_TEXT = "Cursos";
    private static final String TABLE_TITLE_TEXT = "Título";
    private static final String TABLE_HOURS_TEXT = "Horas";
    private static final String TABLE_LEVEL_TEXT = "Nivel";
    private static final String TABLE_TEACHER_TEXT = "Profesor";
    private final CoursesReadService coursesReadService;

    public CoursesListBox(CoursesReadService coursesReadService) {
        this.coursesReadService = coursesReadService;
    }

    public String getHtml() {
        StringBuilder html = new StringBuilder();
        html.append("<div>");
        html.append("<h1>" + LIST_TITLE_TEXT + "</h1>");
        html.append("<div class=\"courseTable\">");
        html.append("<div class=\"courseTableHeader\">");
        html.append("<div class=\"courseTableCell\">" + TABLE_TITLE_TEXT + "</div>");
        html.append("<div class=\"courseTableCell\">" + TABLE_HOURS_TEXT + "</div>");
        html.append("<div class=\"courseTableCell\">" + TABLE_LEVEL_TEXT + "</div>");
        html.append("<div class=\"courseTableCell\">" + TABLE_TEACHER_TEXT + "</div>");
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
            html.append("<div class=\"courseTableCell\">");
            html.append(course.getTeacher().map(this::getTeacherCell).orElse("-"));
            html.append("</div>");
            html.append("</div>");
        }
        html.append("</div>");

        return html.toString();
    }

    private String getTeacherCell(Teacher teacher) {
        return teacher.getName() + " (" + teacher.getMail() + ")";
    }
}