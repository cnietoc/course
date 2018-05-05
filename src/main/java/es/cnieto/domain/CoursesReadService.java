package es.cnieto.domain;

import java.util.List;

public class CoursesReadService {
    private static final int ITEMS_PER_PAGE = 3;
    private final CoursesRepository coursesRepository;

    public CoursesReadService(CoursesRepository coursesRepository) {
        this.coursesRepository = coursesRepository;
    }

    public List<Course> readActives() {
        return coursesRepository.findByActivesOrderByTitle();
    }

    public List<Course> readActivesPaginatedOrderByTitle(int page) {
        return coursesRepository.findByActivesPaginatedOrderByTitle(page, ITEMS_PER_PAGE);
    }

    public int activesPages() {
        return (coursesRepository.countActives() + ITEMS_PER_PAGE - 1) / ITEMS_PER_PAGE;
    }

}
