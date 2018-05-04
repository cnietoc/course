package es.cnieto.domain;

import java.util.List;

public class CoursesReadService {
    private final CoursesRepository coursesRepository;

    public CoursesReadService(CoursesRepository coursesRepository) {
        this.coursesRepository = coursesRepository;
    }

    public List<Course> readActivesOrderByTitle() {
        return coursesRepository.findByActivesOrderByTitle();
    }
}
