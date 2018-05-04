package es.cnieto.servlet.rest;

import es.cnieto.domain.Course;
import es.cnieto.domain.CourseLevel;

class CourseRestConverter {
    CourseRest from(Course course) {
        CourseRest courseRest = new CourseRest();
        courseRest.setId(course.getId());
        courseRest.setTitle(course.getTitle());
        courseRest.setActive(course.isActive());
        courseRest.setHours(course.getHours());
        courseRest.setLevel(from(course.getCourseLevel()));

        return courseRest;
    }

    private CourseLevelRest from(CourseLevel courseLevel) {
        CourseLevelRest courseLevelRest = new CourseLevelRest();
        courseLevelRest.setId(courseLevel.getId());
        courseLevelRest.setLevel(courseLevel.getLevel());

        return courseLevelRest;
    }
}
