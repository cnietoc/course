package es.cnieto.database;

import es.cnieto.domain.Course;
import es.cnieto.domain.CourseLevel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CoursesDAO {
    private static final String SQL_FIND_BY_ACTIVES_ORDER_BY_TITLE = "SELECT ID, TITLE, ACTIVE, HOURS, LEVEL_ID FROM COURSE WHERE ACTIVE = TRUE ORDER BY TITLE ASC";
    private static final String SQL_CREATE_COURSE = "INSERT INTO COURSE(TITLE, ACTIVE, HOURS, LEVEL_ID) VALUES (?, ?, ?, ?)";
    private final DatabaseManager databaseManager;
    private final CourseLevelsDAO courseLevelsDAO;

    public CoursesDAO(DatabaseManager databaseManager, CourseLevelsDAO courseLevelsDAO) {
        this.databaseManager = databaseManager;
        this.courseLevelsDAO = courseLevelsDAO;
    }

    List<Course> findByActivesOrderedByTitle() throws SQLException {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_ACTIVES_ORDER_BY_TITLE);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            List<Course> courses = new ArrayList<>();

            while (resultSet.next()) {
                courses.add(
                        new Course(
                                resultSet.getInt("ID"),
                                resultSet.getString("TITLE"),
                                resultSet.getBoolean("ACTIVE"),
                                resultSet.getInt("HOURS"),
                                courseLevelsDAO.findById(resultSet.getInt("LEVEL_ID")).orElse(null)
                        ));
            }
            return courses;
        }
    }

    public void create(String title, boolean active, int hours, CourseLevel courseLevel) throws SQLException {
        try (
                Connection connection = databaseManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_COURSE)
        ) {
            preparedStatement.setString(1, title);
            preparedStatement.setBoolean(2, active);
            preparedStatement.setInt(3, hours);
            preparedStatement.setInt(4, courseLevel.getId());
            preparedStatement.executeUpdate();
        }
    }
}
