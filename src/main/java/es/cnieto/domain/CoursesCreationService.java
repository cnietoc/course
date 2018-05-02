package es.cnieto.domain;

public class CoursesCreationService {
    private final CoursesRepository coursesRepository;
    private final CourseLevelsRepository courseLevelsRepository;
    private final CourseTitleValidator courseTitleValidator = new CourseTitleValidator();
    private final CourseActiveValidator courseActiveValidator = new CourseActiveValidator();
    private final CourseHoursValidator courseHoursValidator = new CourseHoursValidator();

    public CoursesCreationService(CoursesRepository coursesRepository, CourseLevelsRepository courseLevelsRepository) {
        this.coursesRepository = coursesRepository;
        this.courseLevelsRepository = courseLevelsRepository;
    }

    public void create(String title, Boolean active, Integer hours, Integer levelId) throws CourseValidationException {
        courseTitleValidator.validate(title);
        courseActiveValidator.validate(active);
        courseHoursValidator.validate(hours);
        coursesRepository.create(title,
                active,
                hours,
                findCourseLevel(levelId));
    }

    private CourseLevel findCourseLevel(Integer levelId) throws CourseValidationException {
        return courseLevelsRepository.findById(levelId).orElseThrow(() -> new CourseValidationException("Nivel del curso no válido"));
    }
}
