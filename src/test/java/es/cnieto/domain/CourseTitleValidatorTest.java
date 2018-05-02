package es.cnieto.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseTitleValidatorTest {

    private static final String VALID_TITLE = "Curso";
    private CourseTitleValidator courseTitleValidator = new CourseTitleValidator();

    @Test
    void validateWithValidTitle() throws CourseValidationException {
        courseTitleValidator.validate(VALID_TITLE);
    }

    @Test
    void validateWithEmptyTitle() {
        assertThrows(CourseValidationException.class,
                () -> courseTitleValidator.validate(""));
    }

    @Test
    void validateWithNullTitle() {
        assertThrows(CourseValidationException.class,
                () -> courseTitleValidator.validate(null));
    }
}