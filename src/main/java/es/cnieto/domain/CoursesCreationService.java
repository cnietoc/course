package es.cnieto.domain;

public class CoursesCreationService {
    private final CoursesRepository coursesRepository;
    private final CourseTitleValidator courseTitleValidator = new CourseTitleValidator();
    private final CourseActiveValidator courseActiveValidator = new CourseActiveValidator();
    private final CourseHoursValidator courseHoursValidator = new CourseHoursValidator();

    public CoursesCreationService(CoursesRepository coursesRepository) {
        this.coursesRepository = coursesRepository;
    }

    public void create(String title, Boolean active, Integer hours) throws CourseValidationException {
        courseTitleValidator.validate(title);
        courseActiveValidator.validate(active);
        courseHoursValidator.validate(hours);

        coursesRepository.create(title, active, hours);
    }
}
