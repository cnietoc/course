package es.cnieto.database;

import es.cnieto.domain.Course;
import es.cnieto.domain.CourseLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class CoursesJDBCRepositoryTest {
    private static final String COURSE_ONE_TITLE = "BB Course one";
    private static final boolean COURSE_ONE_ACTIVE = true;
    private static final int COURSE_ONE_HOURS = 180;
    private static final String COURSE_TWO_TITLE = "AA Course two";
    private static final boolean COURSE_TWO_ACTIVE = true;
    private static final int COURSE_TWO_HOURS = 90;
    private static final String COURSE_THREE_TITLE = "CC Course three";
    private static final boolean COURSE_THREE_ACTIVE = false;
    private static final int COURSE_THREE_HOURS = 30;
    private static final String COURSE_LEVEL_ONE = "Básico";
    private static final int COURSE_LEVEL_ONE_ID = 1;

    private CoursesJDBCRepository coursesJDBCRepository;
    private DatabaseManager databaseManager;

    @BeforeEach
    void setup() throws SQLException {
        this.databaseManager = new DatabaseManager("memory:myDB");
        CourseLevelsDAO courseLevelsDAO = new CourseLevelsDAO(databaseManager);
        CoursesDAO coursesDAO = new CoursesDAO(databaseManager, courseLevelsDAO);
        this.coursesJDBCRepository = new CoursesJDBCRepository(coursesDAO);
    }

    @AfterEach
    void cleanCourses() throws Throwable {
        try (Connection connection = databaseManager.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE COURSE");
        }
    }

    @Test
    void findByActivesOrderedByName() throws SQLException {
        fillCourseTable();

        List<Course> courses = coursesJDBCRepository.findByActivesOrderByTitle();

        assertIterableEquals(expectedCourses(), courses);
    }

    @Test
    void create() {
        coursesJDBCRepository.create(COURSE_ONE_TITLE, COURSE_ONE_ACTIVE, COURSE_ONE_HOURS, firstCourseLevel());

        assertIterableEquals(singletonList(new Course(1, COURSE_ONE_TITLE, COURSE_ONE_ACTIVE, COURSE_ONE_HOURS, firstCourseLevel())),
                coursesJDBCRepository.findByActivesOrderByTitle());
    }

    private void fillCourseTable() throws SQLException {
        try (Connection connection = databaseManager.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO COURSE(TITLE, ACTIVE, HOURS, LEVEL_ID) " +
                    "VALUES ('" + COURSE_ONE_TITLE + "', " + COURSE_ONE_ACTIVE + ", " + COURSE_ONE_HOURS + " , " + COURSE_LEVEL_ONE_ID + " )");
            statement.executeUpdate("INSERT INTO COURSE(TITLE, ACTIVE, HOURS, LEVEL_ID) " +
                    "VALUES ('" + COURSE_TWO_TITLE + "', " + COURSE_TWO_ACTIVE + ", " + COURSE_TWO_HOURS + " , " + COURSE_LEVEL_ONE_ID + " )");
            statement.executeUpdate("INSERT INTO COURSE(TITLE, ACTIVE, HOURS, LEVEL_ID) " +
                    "VALUES ('" + COURSE_THREE_TITLE + "', " + COURSE_THREE_ACTIVE + ", " + COURSE_THREE_HOURS + " , " + COURSE_LEVEL_ONE_ID + " )");
        }
    }

    private List<Course> expectedCourses() {
        return asList(new Course(2, COURSE_TWO_TITLE, COURSE_TWO_ACTIVE, COURSE_TWO_HOURS, firstCourseLevel()),
                new Course(1, COURSE_ONE_TITLE, COURSE_ONE_ACTIVE, COURSE_ONE_HOURS, firstCourseLevel()));
    }


    private CourseLevel firstCourseLevel() {
        return new CourseLevel(COURSE_LEVEL_ONE_ID, COURSE_LEVEL_ONE);
    }
}