package es.cnieto.domain;

public class CoursesCreationService {
    private final CoursesRepository coursesRepository;

    public CoursesCreationService(CoursesRepository coursesRepository) {
        this.coursesRepository = coursesRepository;
    }

    public void create(String title, Boolean active, Integer hours) {
        coursesRepository.create(title, active, hours);
    }
}
