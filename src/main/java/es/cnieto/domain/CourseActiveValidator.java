package es.cnieto.domain;

class CourseActiveValidator {
    void validate(Boolean active) throws CourseValidationException {
        if (active == null) {
            throw new CourseValidationException("Active should not be null");
        }
    }
}
