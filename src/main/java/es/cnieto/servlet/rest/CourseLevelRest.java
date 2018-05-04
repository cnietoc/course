package es.cnieto.servlet.rest;

import java.util.Objects;

public class CourseLevelRest {
    private Integer id;
    private String level;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseLevelRest that = (CourseLevelRest) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(level, that.level);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, level);
    }

    @Override
    public String toString() {
        return "CourseLevelRest{" +
                "id=" + id +
                ", level='" + level + '\'' +
                '}';
    }
}
