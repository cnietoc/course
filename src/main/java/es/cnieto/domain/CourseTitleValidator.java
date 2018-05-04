package es.cnieto.domain;

public class CourseTitleValidator {
    void validate(String title) throws CourseValidationException {
        if (title == null || title.isEmpty()) {
            throw new CourseValidationException("Title should not be empty");
        }
    }
}
