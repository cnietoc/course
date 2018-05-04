package es.cnieto.database;

import es.cnieto.domain.Teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TeachersDAO {
    private static final String SQL_FIND_ALL = "SELECT ID, NAME, MAIL FROM TEACHER";
    private static final String SQL_FIND_BY_ID = "SELECT ID, NAME, MAIL FROM TEACHER WHERE ID = ?";
    private final DatabaseManager databaseManager;

    public TeachersDAO(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    List<Teacher> findAll() throws SQLException {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return getTeachersFrom(resultSet);
        }
    }

    Optional<Teacher> findById(int teacherId) throws SQLException {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_ID)) {
            preparedStatement.setInt(1, teacherId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Teacher> teachers = getTeachersFrom(resultSet);
                return teachers.stream().findFirst();
            }
        }
    }

    private List<Teacher> getTeachersFrom(ResultSet resultSet) throws SQLException {
        List<Teacher> teachers = new ArrayList<>();

        while (resultSet.next()) {
            teachers.add(
                    new Teacher(
                            resultSet.getInt("ID"),
                            resultSet.getString("NAME"),
                            resultSet.getString("MAIL")
                    ));
        }
        return teachers;
    }
}
