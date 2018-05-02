package es.cnieto.database;

import es.cnieto.domain.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CoursesDAO {
    private static final String SQL_FIND_BY_ACTIVES_ORDER_BY_TITLE = "SELECT ID, TITLE, ACTIVE, HOURS FROM COURSE WHERE ACTIVE = TRUE ORDER BY TITLE ASC";
    private static final String SQL_CREATE_COURSE = "INSERT INTO COURSE(TITLE, ACTIVE, HOURS) VALUES (?, ?, ?)";
    private final DatabaseManager databaseManager;

    public CoursesDAO(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public List<Course> findByActivesOrderedByTitle() throws SQLException {
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
                                resultSet.getInt("HOURS")
                        ));
            }
            return courses;
        }
    }

    public void create(String title, boolean active, int hours) throws SQLException {
        try (
                Connection connection = databaseManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_COURSE)
        ) {
            preparedStatement.setString(1, title);
            preparedStatement.setBoolean(2, active);
            preparedStatement.setInt(3, hours);
            preparedStatement.executeUpdate();
        }
    }
}
