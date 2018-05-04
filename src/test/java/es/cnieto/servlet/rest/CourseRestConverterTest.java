package es.cnieto.servlet.rest;

import es.cnieto.domain.Course;
import es.cnieto.domain.CourseLevel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseRestConverterTest {
    private static final int COURSE_ID = 4;
    private static final String COURSE_TITLE = "title";
    private static final boolean COURSE_ACTIVE = true;
    private static final int COURSE_HOURS = 324;
    private static final int COURSE_LEVEL_ID = 66;
    private static final String COURSE_LEVEL = "level";

    private final CourseRestConverter courseRestConverter = new CourseRestConverter();

    @Test
    void convertFromAsExpected() {
        Course course = new Course(COURSE_ID, COURSE_TITLE, COURSE_ACTIVE, COURSE_HOURS, new CourseLevel(COURSE_LEVEL_ID, COURSE_LEVEL));

        CourseRest courseRest = courseRestConverter.from(course);

        assertEquals(expectedCourseRest(), courseRest);
    }

    private CourseRest expectedCourseRest() {
        CourseRest courseRest = new CourseRest();
        courseRest.setId(COURSE_ID);
        courseRest.setTitle(COURSE_TITLE);
        courseRest.setActive(COURSE_ACTIVE);
        courseRest.setHours(COURSE_HOURS);
        courseRest.setLevel(expectedCourseLevelRest());
        return courseRest;
    }

    private CourseLevelRest expectedCourseLevelRest() {
        CourseLevelRest courseLevelRest = new CourseLevelRest();
        courseLevelRest.setId(COURSE_LEVEL_ID);
        courseLevelRest.setLevel(COURSE_LEVEL);
        return courseLevelRest;
    }

}