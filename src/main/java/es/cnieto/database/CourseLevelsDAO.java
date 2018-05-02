package es.cnieto.database;

import es.cnieto.domain.CourseLevel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseLevelsDAO {
    private static final String SQL_FIND_ALL = "SELECT ID, LEVEL FROM COURSE_LEVEL";
    private static final String SQL_FIND_BY_ID = "SELECT ID, LEVEL FROM COURSE_LEVEL WHERE ID = ?";
    private final DatabaseManager databaseManager;

    public CourseLevelsDAO(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    List<CourseLevel> findAll() throws SQLException {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return getCourseLevelsFrom(resultSet);
        }
    }

    public Optional<CourseLevel> findById(int levelId) throws SQLException {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_ID)) {
            preparedStatement.setInt(1, levelId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<CourseLevel> courses = getCourseLevelsFrom(resultSet);
                return courses.stream().findFirst();
            }
        }
    }

    private List<CourseLevel> getCourseLevelsFrom(ResultSet resultSet) throws SQLException {
        List<CourseLevel> courses = new ArrayList<>();

        while (resultSet.next()) {
            courses.add(
                    new CourseLevel(
                            resultSet.getInt("ID"),
                            resultSet.getString("LEVEL")
                    ));
        }
        return courses;
    }
}
