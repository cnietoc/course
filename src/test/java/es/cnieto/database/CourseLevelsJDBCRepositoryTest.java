package es.cnieto.database;

import es.cnieto.domain.CourseLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class CourseLevelsJDBCRepositoryTest {
    private static final String COURSE_LEVEL_ONE = "Básico";
    private static final String COURSE_LEVEL_TWO = "Intermedio";
    private static final String COURSE_LEVEL_THREE = "Avanzado";

    private CourseLevelsJDBCRepository courseLevelsJDBCRepository;

    @BeforeEach
    void setup() throws SQLException {
        DatabaseManager databaseManager = new DatabaseManager("memory:myDB");
        CourseLevelsDAO courseLevelsDAO = new CourseLevelsDAO(databaseManager);
        this.courseLevelsJDBCRepository = new CourseLevelsJDBCRepository(courseLevelsDAO);
    }

    @Test
    void findAll() {
        List<CourseLevel> courseLevels = courseLevelsJDBCRepository.findAll();

        assertIterableEquals(expectedCourseLevels(), courseLevels);
    }

    private List<CourseLevel> expectedCourseLevels() {
        return asList(new CourseLevel(1, COURSE_LEVEL_ONE),
                new CourseLevel(2, COURSE_LEVEL_TWO),
                new CourseLevel(3, COURSE_LEVEL_THREE));
    }
}