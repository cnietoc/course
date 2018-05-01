package es.cnieto.database;

import es.cnieto.domain.Course;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class CoursesJDBCRepositoryTest {
    private static final String COURSE_ONE_NAME = "Musica para sordos";
    private static final String COURSE_TWO_NAME = "Hipica para caballos";
    private CoursesJDBCRepository coursesJDBCRepository;
    private DatabaseManager databaseManager;

    @BeforeEach
    void setup() throws SQLException {
        this.databaseManager = new DatabaseManager("memory:myDB");
        CoursesDAO coursesDAO = new CoursesDAO(databaseManager);
        this.coursesJDBCRepository = new CoursesJDBCRepository(coursesDAO);
    }

    @AfterEach
    void cleanCourses() throws Throwable {
        try (Connection connection = databaseManager.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE COURSE");
        }
        databaseManager = new DatabaseManager("memory:myDB");
    }

    @Test
    void findByActivesOrderedByName() throws SQLException {
        fillCourseTable();

        List<Course> courses = coursesJDBCRepository.findByActivesOrderedByName();

        assertIterableEquals(expectedCourses(), courses);
    }

    private void fillCourseTable() throws SQLException {
        try (Connection connection = databaseManager.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO COURSE(NAME) VALUES ('" + COURSE_ONE_NAME + "')");
            statement.executeUpdate("INSERT INTO COURSE(NAME) VALUES ('" + COURSE_TWO_NAME + "')");
        }
    }

    private List<Course> expectedCourses() {
        return asList(new Course(2, COURSE_TWO_NAME),
                new Course(1, COURSE_ONE_NAME));
    }

    @Test
    void create() {
        coursesJDBCRepository.create(COURSE_ONE_NAME);

        assertIterableEquals(singletonList(new Course(1, COURSE_ONE_NAME)),
                coursesJDBCRepository.findByActivesOrderedByName());
    }
}