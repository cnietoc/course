package es.cnieto;

import es.cnieto.database.*;
import es.cnieto.domain.*;
import es.cnieto.servlet.html.*;
import es.cnieto.servlet.rest.CourseRestConverter;
import es.cnieto.servlet.rest.RestCourseController;

import java.sql.SQLException;

public abstract class AppContext {
    private static final String DATABASE_PATH = "coursesDB";

    private static DatabaseManager databaseManager;
    private static CoursesDAO coursesDAO;
    private static CoursesDAOOrderConverter coursesDAOOrderConverter;
    private static CourseLevelsDAO coursesLevelsDAO;
    private static TeachersDAO teachersDAO;

    private static CoursesRepository coursesRepository;
    private static CourseLevelsRepository courseLevelsRepository;
    private static TeachersRepository teachersRepository;

    private static CourseTitleValidator courseTitleValidator;
    private static CourseActiveValidator courseActiveValidator;
    private static CourseHoursValidator courseHoursValidator;

    private static CoursesReadService coursesReadService;
    private static CoursesCreationService coursesCreationService;
    private static CourseLevelsReadService courseLevelsReadService;
    private static TeachersReadService teachersReadService;

    private static OrderConverter orderConverter;
    private static LinkCreator linkCreator;
    private static PaginationBox paginationBox;
    private static CoursesListBox coursesListBox;
    private static TitleInput titleInput;
    private static HoursInput hoursInput;
    private static LevelInput levelInput;
    private static ActiveInput activeInput;
    private static TeacherInput teacherInput;
    private static CreateCourseBox createCourseBox;
    private static HtmlController htmlController;

    private static CourseRestConverter courseRestConverter;
    private static RestCourseController restCourseController;

    private AppContext() {
    }

    public static HtmlController getHtmlController() {
        if (htmlController == null)
            htmlController = new HtmlController(getCoursesListBox(), getCreateCourseBox(), getCoursesCreationService());
        return htmlController;
    }

    private static CreateCourseBox getCreateCourseBox() {
        if (createCourseBox == null)
            createCourseBox = new CreateCourseBox(getTitleInput(), getHoursInput(), getLevelInput(), getActiveInput(), getTeacherInput());
        return createCourseBox;
    }

    private static TitleInput getTitleInput() {
        if (titleInput == null) {
            titleInput = new TitleInput();
        }
        return titleInput;
    }

    private static HoursInput getHoursInput() {
        if (hoursInput == null)
            hoursInput = new HoursInput();
        return hoursInput;
    }

    private static LevelInput getLevelInput() {
        if (levelInput == null)
            levelInput = new LevelInput(getCourseLevelsReadService());
        return levelInput;
    }

    private static ActiveInput getActiveInput() {
        if (activeInput == null)
            activeInput = new ActiveInput();
        return activeInput;
    }

    private static TeacherInput getTeacherInput() {
        if (teacherInput == null) {
            teacherInput = new TeacherInput(getTeachersReadService());
        }
        return teacherInput;
    }

    private static CoursesListBox getCoursesListBox() {
        if (coursesListBox == null) {
            coursesListBox = new CoursesListBox(getCoursesReadService(), getOrderConverter(), getLinkCreator(), getPaginationBox());
        }
        return coursesListBox;
    }

    private static OrderConverter getOrderConverter() {
        if (orderConverter == null)
            orderConverter = new OrderConverter();
        return orderConverter;
    }

    public static PaginationBox getPaginationBox() {
        if (paginationBox == null)
            paginationBox = new PaginationBox(getCoursesReadService(), getLinkCreator());
        return paginationBox;
    }

    public static LinkCreator getLinkCreator() {
        if (linkCreator == null)
            linkCreator = new LinkCreator();
        return linkCreator;
    }

    public static RestCourseController getRestCourseController() {
        if (restCourseController == null)
            restCourseController = new RestCourseController(getCoursesReadService(), getCoursesCreationService(), getCourseRestConverter());

        return restCourseController;
    }

    private static CourseRestConverter getCourseRestConverter() {
        if (courseRestConverter == null)
            courseRestConverter = new CourseRestConverter();
        return courseRestConverter;
    }

    private static CoursesReadService getCoursesReadService() {
        if (coursesReadService == null)
            coursesReadService = new CoursesReadService(getCoursesRepository());

        return coursesReadService;
    }

    private static CoursesCreationService getCoursesCreationService() {
        if (coursesCreationService == null)
            coursesCreationService = new CoursesCreationService(getCourseTitleValidator(), getCourseActiveValidator(), getCourseHoursValidator(), getCoursesRepository(), getCourseLevelsRepository(), getTeachersRepository());

        return coursesCreationService;
    }

    private static CourseLevelsReadService getCourseLevelsReadService() {
        if (courseLevelsReadService == null)
            courseLevelsReadService = new CourseLevelsReadService(getCourseLevelsRepository());

        return courseLevelsReadService;
    }

    private static TeachersReadService getTeachersReadService() {
        if (teachersReadService == null)
            teachersReadService = new TeachersReadService(getTeachersRepository());
        return teachersReadService;
    }

    private static CourseTitleValidator getCourseTitleValidator() {
        if (courseTitleValidator == null)
            courseTitleValidator = new CourseTitleValidator();
        return courseTitleValidator;
    }

    private static CourseActiveValidator getCourseActiveValidator() {
        if (courseActiveValidator == null)
            courseActiveValidator = new CourseActiveValidator();
        return courseActiveValidator;
    }

    private static CourseHoursValidator getCourseHoursValidator() {
        if (courseHoursValidator == null)
            courseHoursValidator = new CourseHoursValidator();
        return courseHoursValidator;
    }

    private static CoursesRepository getCoursesRepository() {
        if (coursesRepository == null)
            coursesRepository = new CoursesJDBCRepository(getCoursesDAO(), getCoursesDAOOrderConverter());

        return coursesRepository;
    }

    private static CourseLevelsRepository getCourseLevelsRepository() {
        if (courseLevelsRepository == null)
            courseLevelsRepository = new CourseLevelsJDBCRepository(getCoursesLevelsDAO());

        return courseLevelsRepository;
    }

    private static TeachersRepository getTeachersRepository() {
        if (teachersRepository == null)
            teachersRepository = new TeachersJDBCRepository(getTeachersDAO());
        return teachersRepository;
    }

    private static CoursesDAO getCoursesDAO() {
        if (coursesDAO == null)
            coursesDAO = new CoursesDAO(getDatabaseManager(), getCoursesLevelsDAO(), getTeachersDAO());

        return coursesDAO;
    }

    private static CoursesDAOOrderConverter getCoursesDAOOrderConverter() {
        if (coursesDAOOrderConverter == null)
            coursesDAOOrderConverter = new CoursesDAOOrderConverter();

        return coursesDAOOrderConverter;
    }

    private static CourseLevelsDAO getCoursesLevelsDAO() {
        if (coursesLevelsDAO == null)
            coursesLevelsDAO = new CourseLevelsDAO(getDatabaseManager());

        return coursesLevelsDAO;
    }

    private static TeachersDAO getTeachersDAO() {
        if (teachersDAO == null)
            teachersDAO = new TeachersDAO(getDatabaseManager());

        return teachersDAO;
    }

    private static DatabaseManager getDatabaseManager() {
        if (databaseManager == null)
            try {
                databaseManager = new DatabaseManager(DATABASE_PATH);
            } catch (SQLException e) {
                throw new AppInitializationException("Error initializing database", e);
            }

        return databaseManager;
    }

    private static class AppInitializationException extends RuntimeException {
        AppInitializationException(String message, Exception e) {
            super(message, e);
        }
    }
}
