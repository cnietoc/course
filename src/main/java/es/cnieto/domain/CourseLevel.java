package es.cnieto.domain;

import java.util.Objects;

public class CourseLevel {
    private final int id;
    private final String level;

    public CourseLevel(int id, String level) {
        this.id = id;
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public String getLevel() {
        return level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseLevel that = (CourseLevel) o;
        return id == that.id &&
                Objects.equals(level, that.level);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, level);
    }

    @Override
    public String toString() {
        return "CourseLevel{" +
                "id=" + id +
                ", level='" + level + '\'' +
                '}';
    }
}
