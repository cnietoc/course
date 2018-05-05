package es.cnieto.database;

import es.cnieto.domain.Course;
import es.cnieto.domain.CourseLevel;
import es.cnieto.domain.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CoursesDAO {
    private static final String SQL_FIND_BY_ACTIVES_ORDER_BY_TITLE = "SELECT ID, TITLE, ACTIVE, HOURS, LEVEL_ID, TEACHER_ID FROM COURSE WHERE ACTIVE = TRUE ORDER BY TITLE ASC";
    private static final String SQL_CREATE_COURSE = "INSERT INTO COURSE(TITLE, ACTIVE, HOURS, LEVEL_ID, TEACHER_ID) VALUES (?, ?, ?, ?, ?)";
    private static final String PAGINATED_SQL_FRAGMENT = " OFFSET ? ROWS FETCH FIRST ? ROWS ONLY";
    private static final String SQL_COUNT = "SELECT COUNT(*) FROM COURSE WHERE ACTIVE = TRUE ";
    private final DatabaseManager databaseManager;
    private final CourseLevelsDAO courseLevelsDAO;
    private final TeachersDAO teachersDAO;

    public CoursesDAO(DatabaseManager databaseManager, CourseLevelsDAO courseLevelsDAO, TeachersDAO teachersDAO) {
        this.databaseManager = databaseManager;
        this.courseLevelsDAO = courseLevelsDAO;
        this.teachersDAO = teachersDAO;
    }

    List<Course> findByActivesOrderByTitle() throws SQLException {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_ACTIVES_ORDER_BY_TITLE);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return getCoursesFrom(resultSet);
        }
    }

    List<Course> findByActivesPaginatedOrderByTitle(int page, int itemsPerPage) throws SQLException {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_ACTIVES_ORDER_BY_TITLE + PAGINATED_SQL_FRAGMENT)) {
            {
                preparedStatement.setInt(1, calculateOffsetFrom(page, itemsPerPage));
                preparedStatement.setInt(2, itemsPerPage);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return getCoursesFrom(resultSet);
                }

            }
        }
    }

    void create(String title, boolean active, int hours, CourseLevel courseLevel, Teacher teacher) throws
            SQLException {
        try (
                Connection connection = databaseManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_COURSE)
        ) {
            preparedStatement.setString(1, title);
            preparedStatement.setBoolean(2, active);
            preparedStatement.setInt(3, hours);
            preparedStatement.setInt(4, courseLevel.getId());
            if (teacher != null) {
                preparedStatement.setInt(5, teacher.getId());
            } else {
                preparedStatement.setNull(5, Types.INTEGER);
            }
            preparedStatement.executeUpdate();
        }
    }

    int countActives() throws SQLException {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_COUNT);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            return resultSet.next() ? resultSet.getInt(1) : 0;
        }
    }

    private int calculateOffsetFrom(int page, int itemsPerPage) {
        return (page - 1) * itemsPerPage;
    }

    private List<Course> getCoursesFrom(ResultSet resultSet) throws SQLException {
        List<Course> courses = new ArrayList<>();

        while (resultSet.next()) {
            courses.add(
                    new Course(
                            resultSet.getInt("ID"),
                            resultSet.getString("TITLE"),
                            resultSet.getBoolean("ACTIVE"),
                            resultSet.getInt("HOURS"),
                            courseLevelsDAO.findById(resultSet.getInt("LEVEL_ID")).orElse(null),
                            getTeacherFrom(resultSet)
                    ));
        }
        return courses;
    }

    private Teacher getTeacherFrom(ResultSet resultSet) throws SQLException {
        return Optional.ofNullable(resultSet.getObject("TEACHER_ID", Integer.class))
                .flatMap(teacherId -> {
                    try {
                        return teachersDAO.findById(teacherId);
                    } catch (SQLException e) {
                        return Optional.empty();
                    }
                })
                .orElse(null);
    }
}
