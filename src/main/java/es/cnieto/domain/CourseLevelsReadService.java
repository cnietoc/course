package es.cnieto.domain;

import java.util.List;

public class CourseLevelsReadService {
    private final CourseLevelsRepository courseLevelsRepository;

    public CourseLevelsReadService(CourseLevelsRepository courseLevelsRepository) {
        this.courseLevelsRepository = courseLevelsRepository;
    }

    public List<CourseLevel> readLevels() {
        return courseLevelsRepository.findAll();
    }
}
