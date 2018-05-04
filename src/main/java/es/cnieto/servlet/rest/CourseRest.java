package es.cnieto.servlet.rest;

import java.util.Objects;

public class CourseRest {
    private Integer id;
    private String title;
    private Boolean active;
    private Integer hours;
    private CourseLevelRest level;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public CourseLevelRest getLevel() {
        return level;
    }

    public void setLevel(CourseLevelRest level) {
        this.level = level;
    }

    public Integer getLevelId() {
        return level != null ? level.getId() : null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseRest that = (CourseRest) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(active, that.active) &&
                Objects.equals(hours, that.hours) &&
                Objects.equals(level, that.level);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, active, hours, level);
    }

    @Override
    public String toString() {
        return "CourseRest{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", active=" + active +
                ", hours=" + hours +
                ", level=" + level +
                '}';
    }


}
