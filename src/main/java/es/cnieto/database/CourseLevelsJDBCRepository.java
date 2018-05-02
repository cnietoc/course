package es.cnieto.database;

import es.cnieto.domain.CourseLevel;
import es.cnieto.domain.CourseLevelsRepository;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CourseLevelsJDBCRepository implements CourseLevelsRepository {
    private static final Logger LOG = Logger.getLogger("es.cnieto.database.CourseLevelsJDBCRepository");
    private final CourseLevelsDAO courseLevelsDAO;

    public CourseLevelsJDBCRepository(CourseLevelsDAO courseLevelsDAO) {
        this.courseLevelsDAO = courseLevelsDAO;
    }

    @Override
    public List<CourseLevel> findAll() {
        try {
            return courseLevelsDAO.findAll();
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error reading Course Levels", e);
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<CourseLevel> findById(Integer levelId) {
        if (levelId == null)
            return Optional.empty();
        try {
            return courseLevelsDAO.findById(levelId);
        } catch (SQLException e) {
            return Optional.empty();
        }
    }
}
