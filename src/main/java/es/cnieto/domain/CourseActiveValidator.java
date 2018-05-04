package es.cnieto.domain;

public class CourseActiveValidator {
    void validate(Boolean active) throws CourseValidationException {
        if (active == null) {
            throw new CourseValidationException("Active should not be null");
        }
    }
}
