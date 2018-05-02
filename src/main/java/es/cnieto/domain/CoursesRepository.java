package es.cnieto.domain;

import java.util.List;

public interface CoursesRepository {
    List<Course> findByActivesOrderedByTitle();

    void create(String title, boolean active, int hours, CourseLevel courseLevel);
}
