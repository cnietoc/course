package es.cnieto.database;

import es.cnieto.domain.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class TeachersJDBCRepositoryTest {
    private static final String TEACHER_ONE_NAME = "Profesor Bacterio";
    private static final String TEACHER_ONE_MAIL = "bacterio@superprofe.com";
    private static final String TEACHER_TWO_NAME = "Profesor Xavier";
    private static final String TEACHER_TWO_MAIL = "xmen@superprofe.com";

    private TeachersJDBCRepository teachersJDBCRepository;

    @BeforeEach
    void setup() throws SQLException {
        DatabaseManager databaseManager = new DatabaseManager("memory:myDB");
        TeachersDAO teachersDAO = new TeachersDAO(databaseManager);
        this.teachersJDBCRepository = new TeachersJDBCRepository(teachersDAO);
    }

    @Test
    void findAll() {
        List<Teacher> teachers = teachersJDBCRepository.findAll();

        assertIterableEquals(expectedTeachers(), teachers);
    }

    private List<Teacher> expectedTeachers() {
        return asList(new Teacher(1, TEACHER_ONE_NAME, TEACHER_ONE_MAIL),
                new Teacher(2, TEACHER_TWO_NAME, TEACHER_TWO_MAIL));
    }
}