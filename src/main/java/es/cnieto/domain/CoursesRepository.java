package es.cnieto.domain;

import java.util.List;

public interface CoursesRepository {
    List<Course> findByActivesOrderByTitle();

    List<Course> findByActivesPaginatedOrderByTitle(int page, int itemsPerPage);

    void create(String title, boolean active, int hours, CourseLevel courseLevel, Teacher teacher);

    int countActives();
}
