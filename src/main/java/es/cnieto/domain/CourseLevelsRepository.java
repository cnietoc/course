package es.cnieto.domain;

import java.util.List;
import java.util.Optional;

public interface CourseLevelsRepository {
    List<CourseLevel> findAll();

    Optional<CourseLevel> findById(Integer levelId);
}
