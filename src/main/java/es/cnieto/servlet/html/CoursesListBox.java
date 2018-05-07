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
    private final LinkCreator linkCreator;
    private final PaginationBox paginationBox;

    public CoursesListBox(CoursesReadService coursesReadService, OrderConverter orderConverter, LinkCreator linkCreator, PaginationBox paginationBox) {
        this.coursesReadService = coursesReadService;
        this.orderConverter = orderConverter;
        this.linkCreator = linkCreator;
        this.paginationBox = paginationBox;
    }

    public String getHtml(HttpServletRequest request) {
        int currentPage = getPageFrom(request);
        OrderParameter orderParameter = getOrderFrom(request);

        StringBuilder html = new StringBuilder();

        html.append("<div>");
        html.append("<h1>" + LIST_TITLE_TEXT + "</h1>");
        html.append("<div class=\"courseTable\">");

        printHeader(html);

        List<Course> courses = coursesReadService.readActivesPaginatedOrderBy(currentPage, orderConverter.from(orderParameter));
        courses.forEach(course -> printRow(html, course));
        html.append("</div>");

        paginationBox.printPagination(currentPage, orderParameter, html);

        return html.toString();
    }

    private void printRow(StringBuilder html, Course course) {
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

    private void printHeader(StringBuilder html) {
        html.append("<div class=\"courseTableHeader\">");
        html.append("<div class=\"courseTableCell\">" + linkCreator.createLinkFor(TABLE_TITLE_TEXT, 1, OrderParameter.TITLE) + "</div>");
        html.append("<div class=\"courseTableCell\">" + linkCreator.createLinkFor(TABLE_HOURS_TEXT, 1, OrderParameter.HOURS) + "</div>");
        html.append("<div class=\"courseTableCell\">" + linkCreator.createLinkFor(TABLE_LEVEL_TEXT, 1, OrderParameter.LEVEL) + "</div>");
        html.append("<div class=\"courseTableCell\">" + TABLE_TEACHER_TEXT + "</div>");
        html.append("</div>");
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

    private String getTeacherCell(Teacher teacher) {
        return teacher.getName() + " (" + teacher.getMail() + ")";
    }
}