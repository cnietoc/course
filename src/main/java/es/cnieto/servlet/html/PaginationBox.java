package es.cnieto.servlet.html;

import es.cnieto.domain.CoursesReadService;

import java.io.Serializable;

public class PaginationBox implements Serializable {
    private final CoursesReadService coursesReadService;
    private final LinkCreator linkCreator;

    public PaginationBox(CoursesReadService coursesReadService, LinkCreator linkCreator) {
        this.coursesReadService = coursesReadService;
        this.linkCreator = linkCreator;
    }

    void printPagination(int currentPage, OrderParameter orderParameter, StringBuilder html) {

        int pages = coursesReadService.activesPages();
        html.append("<div class=\"pagination\">");

        for (int page = 1; page <= pages; page++) {
            html.append(page == currentPage ? "<b>" : "");
            html.append(linkCreator.createLinkFor(String.valueOf(page), page, orderParameter));
            html.append(page == currentPage ? "</b>" : "");
        }
        html.append("</div>");
    }
}