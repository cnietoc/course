package es.cnieto.domain;

import java.util.Objects;

public class Course {
    private final int id;
    private final String title;
    private final boolean active;
    private final int hours;

    public Course(int id, String title, boolean active, int hours) {
        this.id = id;
        this.title = title;
        this.active = active;
        this.hours = hours;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id &&
                active == course.active &&
                hours == course.hours &&
                Objects.equals(title, course.title);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, title, active, hours);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", active=" + active +
                ", hours=" + hours +
                '}';
    }
}
