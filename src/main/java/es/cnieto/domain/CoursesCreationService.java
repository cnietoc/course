package es.cnieto.domain;

import java.util.Optional;

public class CoursesCreationService {
    private final CourseTitleValidator courseTitleValidator;
    private final CourseActiveValidator courseActiveValidator;
    private final CourseHoursValidator courseHoursValidator;

    private final CoursesRepository coursesRepository;
    private final CourseLevelsRepository courseLevelsRepository;
    private final TeachersRepository teachersRepository;

    public CoursesCreationService(CourseTitleValidator courseTitleValidator, CourseActiveValidator courseActiveValidator, CourseHoursValidator courseHoursValidator, CoursesRepository coursesRepository, CourseLevelsRepository courseLevelsRepository, TeachersRepository teachersRepository) {
        this.courseTitleValidator = courseTitleValidator;
        this.courseActiveValidator = courseActiveValidator;
        this.courseHoursValidator = courseHoursValidator;
        this.coursesRepository = coursesRepository;
        this.courseLevelsRepository = courseLevelsRepository;
        this.teachersRepository = teachersRepository;
    }

    public void create(String title, Boolean active, Integer hours, Integer levelId, Integer teacherId) throws CourseValidationException {
        courseTitleValidator.validate(title);
        courseActiveValidator.validate(active);
        courseHoursValidator.validate(hours);
        coursesRepository.create(title,
                active,
                hours,
                findCourseLevel(levelId),
                findTeacher(teacherId).orElse(null));
    }

    private CourseLevel findCourseLevel(Integer levelId) throws CourseValidationException {
        return courseLevelsRepository.findById(levelId).orElseThrow(() -> new CourseValidationException("Nivel del curso no válido"));
    }

    private Optional<Teacher> findTeacher(Integer teacherId) {
        return teachersRepository.findById(teacherId);
    }
}
