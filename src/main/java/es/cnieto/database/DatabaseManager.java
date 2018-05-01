package es.cnieto.database;

import java.sql.*;
import java.util.logging.Logger;

public class DatabaseManager {
    private static final Logger LOG = Logger.getLogger("es.cnieto.database.DatabaseManager");
    private static final String JDBC_DATABASE = "jdbc:derby:";
    private final String databasePath;

    public DatabaseManager(String databasePath) throws SQLException {
        this.databasePath = databasePath;
        try (Connection connection = getConnection()) {
            if (!isCourseTableCreated(connection)) {
                createCourseTable(connection);
            }
            LOG.info("Database initialized");
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_DATABASE + databasePath + ";collation=TERRITORY_BASED:PRIMARY;create=true");
    }


    private boolean isCourseTableCreated(Connection connection) throws SQLException {
        boolean courseTableCreated = false;
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet resultSet = metaData
                .getTables(null, null, null, new String[]{"TABLE"});
        while (resultSet.next()) {
            String table = resultSet.getString("TABLE_NAME");
            if (table.equals("COURSE")) {
                courseTableCreated = true;
            }
        }
        return courseTableCreated;
    }

    private void createCourseTable(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "CREATE TABLE COURSE ( " +
                        "ID INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                        "NAME VARCHAR(255)" +
                        ")");
        preparedStatement.executeUpdate();
        LOG.info("COURSE Table Created");
    }

    @Override
    protected void finalize() {
        try {
            DriverManager.getConnection(JDBC_DATABASE + databasePath + ";shutdown=true");
        } catch (SQLException e) {
            // finalized
        }
    }
}
