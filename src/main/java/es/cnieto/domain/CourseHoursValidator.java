package es.cnieto.domain;

class CourseHoursValidator {
    void validate(Integer hours) throws CourseValidationException {
        if (hours == null || hours <= 0) {
            throw new CourseValidationException("Hours should be positive");
        }
    }
}
