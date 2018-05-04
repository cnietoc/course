package es.cnieto.domain;

import java.util.List;
import java.util.Optional;

public interface TeachersRepository {
    List<Teacher> findAll();

    Optional<Teacher> findById(Integer teacherId);
}
