package es.cnieto.servlet.rest;

import es.cnieto.domain.Course;
import es.cnieto.domain.CourseLevel;
import es.cnieto.domain.Teacher;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseRestConverterTest {
    private static final int COURSE_ID = 4;
    private static final String COURSE_TITLE = "title";
    private static final boolean COURSE_ACTIVE = true;
    private static final int COURSE_HOURS = 324;
    private static final int COURSE_LEVEL_ID = 66;
    private static final String COURSE_LEVEL = "level";
    private static final int TEACHER_ID = 33;
    private static final String TEACHER_NAME = "teachername";
    private static final String TEACHER_MAIL = "teacher@mail.com";

    private final CourseRestConverter courseRestConverter = new CourseRestConverter();

    @Test
    void convertWhenThereAreTeacher() {
        Course course = new Course(COURSE_ID, COURSE_TITLE, COURSE_ACTIVE, COURSE_HOURS, new CourseLevel(COURSE_LEVEL_ID, COURSE_LEVEL), new Teacher(TEACHER_ID, TEACHER_NAME, TEACHER_MAIL));

        CourseRest courseRest = courseRestConverter.from(course);

        assertEquals(expectedCourseRestWithTeacher(expectedTeacherRest()), courseRest);
    }

    @Test
    void convertWhenThereAreNotTeacher() {
        Course course = new Course(COURSE_ID, COURSE_TITLE, COURSE_ACTIVE, COURSE_HOURS, new CourseLevel(COURSE_LEVEL_ID, COURSE_LEVEL), null);

        CourseRest courseRest = courseRestConverter.from(course);

        assertEquals(expectedCourseRestWithTeacher(null), courseRest);
    }

    private CourseRest expectedCourseRestWithTeacher(TeacherRest teacherRest) {
        CourseRest courseRest = new CourseRest();
        courseRest.setId(COURSE_ID);
        courseRest.setTitle(COURSE_TITLE);
        courseRest.setActive(COURSE_ACTIVE);
        courseRest.setHours(COURSE_HOURS);
        courseRest.setLevel(expectedCourseLevelRest());
        courseRest.setTeacher(teacherRest);
        return courseRest;
    }

    private CourseLevelRest expectedCourseLevelRest() {
        CourseLevelRest courseLevelRest = new CourseLevelRest();
        courseLevelRest.setId(COURSE_LEVEL_ID);
        courseLevelRest.setLevel(COURSE_LEVEL);
        return courseLevelRest;
    }

    private TeacherRest expectedTeacherRest() {
        TeacherRest teacherRest = new TeacherRest();
        teacherRest.setId(TEACHER_ID);
        teacherRest.setName(TEACHER_NAME);
        teacherRest.setMail(TEACHER_MAIL);
        return teacherRest;
    }
}