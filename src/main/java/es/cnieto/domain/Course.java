package es.cnieto.domain;

import java.util.Objects;
import java.util.Optional;

public class Course {
    private final int id;
    private final String title;
    private final boolean active;
    private final int hours;
    private final CourseLevel courseLevel;
    private final Teacher teacher;

    public Course(int id, String title, boolean active, int hours, CourseLevel courseLevel, Teacher teacher) {
        this.id = id;
        this.title = title;
        this.active = active;
        this.hours = hours;
        this.courseLevel = courseLevel;
        this.teacher = teacher;
    }

    public String getLevel() {
        return courseLevel.getLevel();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isActive() {
        return active;
    }

    public int getHours() {
        return hours;
    }

    public CourseLevel getCourseLevel() {
        return courseLevel;
    }

    public Optional<Teacher> getTeacher() {
        return Optional.ofNullable(teacher);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id &&
                active == course.active &&
                hours == course.hours &&
                Objects.equals(title, course.title) &&
                Objects.equals(courseLevel, course.courseLevel) &&
                Objects.equals(teacher, course.teacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, active, hours, courseLevel, teacher);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", active=" + active +
                ", hours=" + hours +
                ", courseLevel=" + courseLevel +
                ", teacher=" + teacher +
                '}';
    }
}
