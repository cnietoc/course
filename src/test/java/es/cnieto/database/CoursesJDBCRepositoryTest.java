package es.cnieto.database;

import es.cnieto.domain.Course;
import es.cnieto.domain.CourseLevel;
import es.cnieto.domain.CourseOrder;
import es.cnieto.domain.Teacher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Comparator;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    private static final String COURSE_FOURTH_TITLE = "AB Course four";
    private static final boolean COURSE_FOURTH_ACTIVE = true;
    private static final int COURSE_FOURTH_HOURS = 50;
    private static final String COURSE_LEVEL_ONE = "Básico";
    private static final int COURSE_LEVEL_ONE_ID = 1;
    private static final String COURSE_LEVEL_TWO = "Intermedio";
    private static final int COURSE_LEVEL_TWO_ID = 2;
    private static final int TEACHER_ONE_ID = 1;
    private static final String TEACHER_ONE_NAME = "Profesor Bacterio";
    private static final String TEACHER_ONE_MAIL = "bacterio@superprofe.com";
    private static final int REDUCED_NUMBER_OF_ITEMS_PER_PAGE = 2;
    private static final short TOTAL_ACTIVES_COURSES = 3;

    private CoursesJDBCRepository coursesJDBCRepository;
    private DatabaseManager databaseManager;

    @BeforeEach
    void setup() throws SQLException {
        this.databaseManager = new DatabaseManager("memory:myDB");
        CourseLevelsDAO courseLevelsDAO = new CourseLevelsDAO(databaseManager);
        TeachersDAO teachersDAO = new TeachersDAO(databaseManager);
        CoursesDAO coursesDAO = new CoursesDAO(databaseManager, courseLevelsDAO, teachersDAO);
        this.coursesJDBCRepository = new CoursesJDBCRepository(coursesDAO, new CoursesDAOOrderConverter());
    }

    @AfterEach
    void cleanCourses() throws Throwable {
        try (Connection connection = databaseManager.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE COURSE");
        }
    }

    @Test
    void findByActivesOrderedByTitle() throws SQLException {
        fillCourseTable();

        List<Course> courses = coursesJDBCRepository.findByActivesOrderBy(CourseOrder.TITLE);

        assertIterableEquals(expectedCoursesOrderByTitle(), courses);
    }

    @Test
    void findByActivesOrderedByHours() throws SQLException {
        fillCourseTable();

        List<Course> courses = coursesJDBCRepository.findByActivesOrderBy(CourseOrder.HOURS);

        assertIterableEquals(expectedCoursesOrderByHours(), courses);
    }

    @Test
    void findByActivesOrderedByLevel() throws SQLException {
        fillCourseTable();

        List<Course> courses = coursesJDBCRepository.findByActivesOrderBy(CourseOrder.LEVEL);

        assertIterableEquals(expectedCoursesOrderByLevel(), courses);
    }

    @Test
    void findByActivesPaginatedOrderByTitleWhenIsFirstPage() throws SQLException {
        fillCourseTable();

        List<Course> courses = coursesJDBCRepository.findByActivesPaginatedOrderBy(1, REDUCED_NUMBER_OF_ITEMS_PER_PAGE, CourseOrder.TITLE);

        assertIterableEquals(expectedCoursesOrderByTitle().subList(0, 2), courses);
    }

    @Test
    void findByActivesPaginatedOrderByTitleWhenIsSecondPage() throws SQLException {
        fillCourseTable();

        List<Course> courses = coursesJDBCRepository.findByActivesPaginatedOrderBy(2, REDUCED_NUMBER_OF_ITEMS_PER_PAGE, CourseOrder.TITLE);

        assertIterableEquals(expectedCoursesOrderByTitle().subList(2, 3), courses);
    }

    @Test
    void createWithoutTeacher() {
        coursesJDBCRepository.create(COURSE_ONE_TITLE, COURSE_ONE_ACTIVE, COURSE_ONE_HOURS, firstCourseLevel(), null);

        assertIterableEquals(singletonList(new Course(1, COURSE_ONE_TITLE, COURSE_ONE_ACTIVE, COURSE_ONE_HOURS, firstCourseLevel(), null)),
                coursesJDBCRepository.findByActivesOrderBy(CourseOrder.TITLE));
    }

    @Test
    void createWithTeacher() {
        coursesJDBCRepository.create(COURSE_ONE_TITLE, COURSE_ONE_ACTIVE, COURSE_ONE_HOURS, firstCourseLevel(), firstTeacher());

        assertIterableEquals(singletonList(new Course(1, COURSE_ONE_TITLE, COURSE_ONE_ACTIVE, COURSE_ONE_HOURS, firstCourseLevel(), firstTeacher())),
                coursesJDBCRepository.findByActivesOrderBy(CourseOrder.TITLE));
    }

    @Test
    void countActives() throws SQLException {
        fillCourseTable();

        int count = coursesJDBCRepository.countActives();

        assertEquals(TOTAL_ACTIVES_COURSES, count);
    }

    private void fillCourseTable() throws SQLException {
        try (Connection connection = databaseManager.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO COURSE(TITLE, ACTIVE, HOURS, LEVEL_ID, TEACHER_ID) " +
                    "VALUES ('" + COURSE_ONE_TITLE + "', " + COURSE_ONE_ACTIVE + ", " + COURSE_ONE_HOURS + " , " + COURSE_LEVEL_ONE_ID + ", " + TEACHER_ONE_ID + " )");
            statement.executeUpdate("INSERT INTO COURSE(TITLE, ACTIVE, HOURS, LEVEL_ID) " +
                    "VALUES ('" + COURSE_TWO_TITLE + "', " + COURSE_TWO_ACTIVE + ", " + COURSE_TWO_HOURS + " , " + COURSE_LEVEL_ONE_ID + " )");
            statement.executeUpdate("INSERT INTO COURSE(TITLE, ACTIVE, HOURS, LEVEL_ID) " +
                    "VALUES ('" + COURSE_THREE_TITLE + "', " + COURSE_THREE_ACTIVE + ", " + COURSE_THREE_HOURS + " , " + COURSE_LEVEL_TWO_ID + " )");
            statement.executeUpdate("INSERT INTO COURSE(TITLE, ACTIVE, HOURS, LEVEL_ID) " +
                    "VALUES ('" + COURSE_FOURTH_TITLE + "', " + COURSE_FOURTH_ACTIVE + ", " + COURSE_FOURTH_HOURS + " , " + COURSE_LEVEL_TWO_ID + " )");
        }
    }

    private List<Course> expectedCoursesOrderByTitle() {
        List<Course> courses = activeCourses();
        courses.sort(titleComparator());

        return courses;
    }

    private List<Course> expectedCoursesOrderByHours() {
        List<Course> courses = activeCourses();
        courses.sort(Comparator.comparingInt(Course::getHours).thenComparing(titleComparator()));

        return courses;
    }

    private List<Course> expectedCoursesOrderByLevel() {
        List<Course> courses = activeCourses();
        courses.sort(Comparator.comparing(this::getLevelId).thenComparing(titleComparator()));

        return courses;
    }

    private int getLevelId(Course course) {
        return course.getCourseLevel().getId();
    }

    private Comparator<Course> titleComparator() {
        return (o1, o2) -> o1.getTitle().compareToIgnoreCase(o2.getTitle());
    }

    private List<Course> activeCourses() {
        return asList(new Course(1, COURSE_ONE_TITLE, COURSE_ONE_ACTIVE, COURSE_ONE_HOURS, firstCourseLevel(), firstTeacher()),
                new Course(2, COURSE_TWO_TITLE, COURSE_TWO_ACTIVE, COURSE_TWO_HOURS, firstCourseLevel(), null),
                new Course(4, COURSE_FOURTH_TITLE, COURSE_FOURTH_ACTIVE, COURSE_FOURTH_HOURS, secondCourseLevel(), null));
    }

    private CourseLevel firstCourseLevel() {
        return new CourseLevel(COURSE_LEVEL_ONE_ID, COURSE_LEVEL_ONE);
    }

    private CourseLevel secondCourseLevel() {
        return new CourseLevel(COURSE_LEVEL_TWO_ID, COURSE_LEVEL_TWO);
    }

    private Teacher firstTeacher() {
        return new Teacher(TEACHER_ONE_ID, TEACHER_ONE_NAME, TEACHER_ONE_MAIL);
    }
}