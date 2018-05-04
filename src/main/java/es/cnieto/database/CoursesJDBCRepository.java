package es.cnieto.database;

import es.cnieto.domain.Course;
import es.cnieto.domain.CourseLevel;
import es.cnieto.domain.CoursesRepository;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CoursesJDBCRepository implements CoursesRepository {
    private static final Logger LOG = Logger.getLogger("es.cnieto.database.CoursesJDBCRepository");
    private final CoursesDAO coursesDAO;

    public CoursesJDBCRepository(CoursesDAO coursesDAO) {
        this.coursesDAO = coursesDAO;
    }

    @Override
    public List<Course> findByActivesOrderByTitle() {
        try {
            return coursesDAO.findByActivesOrderByTitle();
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error reading Courses", e);
            return Collections.emptyList();
        }
    }

    @Override
    public void create(String title, boolean active, int hours, CourseLevel courseLevel) {
        try {
            coursesDAO.create(title, active, hours, courseLevel);
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error creating Courses", e);
        }
    }
}
