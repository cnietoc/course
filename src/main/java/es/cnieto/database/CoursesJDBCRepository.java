package es.cnieto.database;

import es.cnieto.domain.*;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CoursesJDBCRepository implements CoursesRepository {
    private static final Logger LOG = Logger.getLogger("es.cnieto.database.CoursesJDBCRepository");
    private final CoursesDAO coursesDAO;
    private final CoursesDAOOrderConverter coursesDAOOrderConverter;

    public CoursesJDBCRepository(CoursesDAO coursesDAO, CoursesDAOOrderConverter coursesDAOOrderConverter) {
        this.coursesDAO = coursesDAO;
        this.coursesDAOOrderConverter = coursesDAOOrderConverter;
    }

    @Override
    public List<Course> findByActivesOrderBy(CourseOrder courseOrder) {
        try {
            return coursesDAO.findByActivesOrderBy(coursesDAOOrderConverter.from(courseOrder));
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error reading Courses", e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<Course> findByActivesPaginatedOrderBy(int page, int itemsPerPage, CourseOrder courseOrder) {
        try {
            return coursesDAO.findByActivesPaginatedOrderBy(page, itemsPerPage, coursesDAOOrderConverter.from(courseOrder));
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error reading Courses", e);
            return Collections.emptyList();
        }
    }

    @Override
    public void create(String title, boolean active, int hours, CourseLevel courseLevel, Teacher teacher) {
        try {
            coursesDAO.create(title, active, hours, courseLevel, teacher);
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error creating Courses", e);
        }
    }

    @Override
    public int countActives() {
        try {
            return coursesDAO.countActives();
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error counting Courses", e);
            return 0;
        }
    }
}
