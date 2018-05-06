package es.cnieto.domain;

import java.util.List;

import static es.cnieto.domain.CourseOrder.TITLE;

public class CoursesReadService {
    private static final int ITEMS_PER_PAGE = 3;
    private final CoursesRepository coursesRepository;

    public CoursesReadService(CoursesRepository coursesRepository) {
        this.coursesRepository = coursesRepository;
    }

    public List<Course> readActives() {
        return coursesRepository.findByActivesOrderBy(TITLE);
    }

    public List<Course> readActivesPaginatedOrderBy(int page, CourseOrder order) {
        return coursesRepository.findByActivesPaginatedOrderBy(page, ITEMS_PER_PAGE, order);
    }

    public int activesPages() {
        return (coursesRepository.countActives() + ITEMS_PER_PAGE - 1) / ITEMS_PER_PAGE;
    }
}
