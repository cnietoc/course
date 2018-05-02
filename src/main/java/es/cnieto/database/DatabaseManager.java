package es.cnieto.database;

import java.sql.*;
import java.util.List;
import java.util.logging.Logger;

import static java.util.Arrays.asList;

public class DatabaseManager {
    private static final Logger LOG = Logger.getLogger("es.cnieto.database.DatabaseManager");
    private static final String JDBC_DATABASE = "jdbc:derby:";

    private static final String COURSE_LEVEL_TABLE_NAME = "COURSE_LEVEL";
    private static final String CREATE_TABLE_COURSE_LEVEL_SQL = "CREATE TABLE COURSE_LEVEL ( " +
            "ID INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
            "LEVEL VARCHAR(255) NOT NULL " +
            ")";
    private static final List<String> COURSE_LEVELS = asList("Básico", "Intermedio", "Avanzado");

    private static final String COURSE_TABLE_NAME = "COURSE";
    private static final String CREATE_TABLE_COURSE_SQL = "CREATE TABLE COURSE ( " +
            "ID INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
            "TITLE VARCHAR(255) NOT NULL, " +
            "ACTIVE BOOLEAN NOT NULL, " +
            "HOURS INTEGER NOT NULL," +
            "LEVEL_ID INTEGER NOT NULL REFERENCES COURSE_LEVEL(ID)" +
            ")";

    private final String databasePath;

    public DatabaseManager(String databasePath) throws SQLException {
        this.databasePath = databasePath;
        try (Connection connection = getConnection()) {
            if (!isTableCreated(COURSE_LEVEL_TABLE_NAME, connection)) {
                createCourseLevelTable(connection);
                fillCourseLevelTable(connection);
            }
            if (!isTableCreated(COURSE_TABLE_NAME, connection)) {
                createCourseTable(connection);
            }
            LOG.info("Database initialized");
        }
    }

    Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_DATABASE + databasePath + ";collation=TERRITORY_BASED:PRIMARY;create=true");
    }

    private boolean isTableCreated(String tableName, Connection connection) throws SQLException {
        boolean courseTableCreated = false;
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet resultSet = metaData
                .getTables(null, null, null, new String[]{"TABLE"});
        while (resultSet.next()) {
            String table = resultSet.getString("TABLE_NAME");
            if (table.equals(tableName)) {
                courseTableCreated = true;
            }
        }
        return courseTableCreated;
    }

    private void createCourseTable(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE_COURSE_SQL);
        preparedStatement.executeUpdate();
        LOG.info("COURSE Table Created");
    }

    private void createCourseLevelTable(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE_COURSE_LEVEL_SQL);
        preparedStatement.executeUpdate();
        LOG.info("COURSE_LEVEL Table Created");
    }

    private void fillCourseLevelTable(Connection connection) throws SQLException {
        for (String level : COURSE_LEVELS) {
            insertCourseLevel(level, connection);
        }
    }

    private void insertCourseLevel(String level, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO COURSE_LEVEL(LEVEL) VALUES (?)");
        preparedStatement.setString(1, level);
        preparedStatement.executeUpdate();
    }
}
