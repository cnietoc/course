package es.cnieto.domain;

import java.util.List;

public class TeachersReadService {
    private final TeachersRepository teachersRepository;

    public TeachersReadService(TeachersRepository teachersRepository) {
        this.teachersRepository = teachersRepository;
    }

    public List<Teacher> readTeachers() {
        return teachersRepository.findAll();
    }
}
