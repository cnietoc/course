package es.cnieto.domain;

import java.util.List;

public interface CoursesRepository {
    List<Course> findByActivesOrderByTitle();

    void create(String title, boolean active, int hours, CourseLevel courseLevel);
}
