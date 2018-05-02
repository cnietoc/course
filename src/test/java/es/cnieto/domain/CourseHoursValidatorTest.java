package es.cnieto.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CourseHoursValidatorTest {

    private static final Integer VALID_HOURS = 180;
    public static final int NEGATIVE_HOURS = -60;
    private CourseHoursValidator courseHoursValidator = new CourseHoursValidator();

    @Test
    void validateWithValidHours() throws CourseValidationException {
        courseHoursValidator.validate(VALID_HOURS);
    }

    @Test
    void validateWithZeroHours() {
        assertThrows(CourseValidationException.class,
                () -> courseHoursValidator.validate(0));
    }

    @Test
    void validateWithNegativeHours() {
        assertThrows(CourseValidationException.class,
                () -> courseHoursValidator.validate(NEGATIVE_HOURS));
    }

    @Test
    void validateWithNullHours() {
        assertThrows(CourseValidationException.class,
                () -> courseHoursValidator.validate(null));
    }

}