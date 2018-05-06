package es.cnieto.domain;

import java.util.List;

public interface CoursesRepository {
    List<Course> findByActivesOrderBy(CourseOrder courseOrder);

    List<Course> findByActivesPaginatedOrderBy(int page, int itemsPerPage, CourseOrder courseOrder);

    void create(String title, boolean active, int hours, CourseLevel courseLevel, Teacher teacher);

    int countActives();
}
