package es.cnieto.database;

import es.cnieto.domain.Teacher;
import es.cnieto.domain.TeachersRepository;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TeachersJDBCRepository implements TeachersRepository {
    private static final Logger LOG = Logger.getLogger("es.cnieto.database.TeachersJDBCRepository");
    private final TeachersDAO teachersDAO;

    public TeachersJDBCRepository(TeachersDAO teachersDAO) {
        this.teachersDAO = teachersDAO;
    }

    @Override
    public List<Teacher> findAll() {
        try {
            return teachersDAO.findAll();
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error reading Teachers", e);
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Teacher> findById(Integer teacherId) {
        if (teacherId == null)
            return Optional.empty();
        try {
            return teachersDAO.findById(teacherId);
        } catch (SQLException e) {
            return Optional.empty();
        }
    }
}
