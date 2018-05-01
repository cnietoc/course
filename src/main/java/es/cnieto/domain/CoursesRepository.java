package es.cnieto.domain;

import java.util.List;

public interface CoursesRepository {
    List<Course> findByActivesOrderedByName();

    void create(String name);
}
