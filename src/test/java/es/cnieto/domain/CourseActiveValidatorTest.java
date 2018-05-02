package es.cnieto.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CourseActiveValidatorTest {
    private CourseActiveValidator courseActiveValidator = new CourseActiveValidator();

    @Test
    void validateWithValidValue() throws CourseValidationException {
        courseActiveValidator.validate(Boolean.TRUE);
    }

    @Test
    void validateWithNullValue() {
        assertThrows(CourseValidationException.class,
                () -> courseActiveValidator.validate(null));
    }
}