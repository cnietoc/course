package es.cnieto.servlet.html;

import es.cnieto.domain.Course;
import es.cnieto.domain.CoursesReadService;
import es.cnieto.domain.Teacher;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

public class CoursesListBox implements Serializable {
    private static final String LIST_TITLE_TEXT = "Cursos";
    private static final String TABLE_TITLE_TEXT = "Título";
    private static final String TABLE_HOURS_TEXT = "Horas";
    private static final String TABLE_LEVEL_TEXT = "Nivel";
    private static final String TABLE_TEACHER_TEXT = "Profesor";
    private static final String PAGE_PARAMETER = "page";
    private static final String ORDER_PARAMETER = "order";
    private final CoursesReadService coursesReadService;
    private final OrderConverter orderConverter;

    public CoursesListBox(CoursesReadService coursesReadService, OrderConverter orderConverter) {
        this.coursesReadService = coursesReadService;
        this.orderConverter = orderConverter;
    }

    public String getHtml(HttpServletRequest request) {
        int currentPage = getPageFrom(request);
        OrderParameter orderParameter = getOrderFrom(request);

        StringBuilder html = new StringBuilder();
        html.append("<div>");
        html.append("<h1>" + LIST_TITLE_TEXT + "</h1>");
        html.append("<div class=\"courseTable\">");
        html.append("<div class=\"courseTableHeader\">");
        html.append("<div class=\"courseTableCell\">" + createLinkFor(TABLE_TITLE_TEXT, 1, OrderParameter.TITLE) + "</div>");
        html.append("<div class=\"courseTableCell\">" + createLinkFor(TABLE_HOURS_TEXT, 1, OrderParameter.HOURS) + "</div>");
        html.append("<div class=\"courseTableCell\">" + createLinkFor(TABLE_LEVEL_TEXT, 1, OrderParameter.LEVEL) + "</div>");
        html.append("<div class=\"courseTableCell\">" + TABLE_TEACHER_TEXT + "</div>");
        html.append("</div>");

        List<Course> courses = coursesReadService.readActivesPaginatedOrderBy(currentPage, orderConverter.from(orderParameter));
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

        printPagination(currentPage, orderParameter, html);

        return html.toString();
    }

    private void printPagination(int currentPage, OrderParameter orderParameter, StringBuilder html) {

        int pages = coursesReadService.activesPages();
        html.append("<div class=\"pagination\">");

        for (int page = 1; page <= pages; page++) {
            html.append(page == currentPage ? "<b>" : "");
            html.append(createLinkFor(String.valueOf(page), page, orderParameter));
            html.append(page == currentPage ? "</b>" : "");
        }
        html.append("</div>");
    }

    private String createLinkFor(String text, int page, OrderParameter orderParameter) {
        return "<a href=\"" + hrefFor(page, orderParameter) + "\" >" + text + "</a>";
    }

    private int getPageFrom(HttpServletRequest request) {
        try {
            return Integer.parseInt(request.getParameter(PAGE_PARAMETER));
        } catch (NumberFormatException | NullPointerException e) {
            return 1;
        }
    }

    private OrderParameter getOrderFrom(HttpServletRequest request) {
        return OrderParameter.findFrom(request.getParameter(ORDER_PARAMETER)).orElse(OrderParameter.TITLE);
    }

    private String hrefFor(int page, OrderParameter orderParameter) {
        return "?page=" + page + "&order=" + orderParameter.getValue();
    }

    private String getTeacherCell(Teacher teacher) {
        return teacher.getName() + " (" + teacher.getMail() + ")";
    }
}