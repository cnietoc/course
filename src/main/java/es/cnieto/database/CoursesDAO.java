package es.cnieto.database;

import es.cnieto.domain.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CoursesDAO {
    private static final String SQL_FIND_BY_ACTIVES_ORDER_BY_NAME = "SELECT ID, NAME FROM COURSE ORDER BY NAME ASC";
    private static final String SQL_CREATE_COURSE = "INSERT INTO COURSE(NAME) VALUES (?)";
    private final DatabaseManager databaseManager;

    public CoursesDAO(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public List<Course> findByActivesOrderedByName() throws SQLException {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_ACTIVES_ORDER_BY_NAME);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            List<Course> courses = new ArrayList<>();

            while (resultSet.next()) {
                courses.add(
                        new Course(
                                resultSet.getInt("ID"),
                                resultSet.getString("NAME")
                        ));
            }
            return courses;
        }
    }

    public void create(String name) throws SQLException {
        try (
                Connection connection = databaseManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_COURSE)
        ) {
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
        }
    }
}
