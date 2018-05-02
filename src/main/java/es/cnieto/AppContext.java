package es.cnieto;

import es.cnieto.database.*;
import es.cnieto.domain.*;

import java.sql.SQLException;

public abstract class AppContext {
    private static final String DATABASE_PATH = "coursesDB";

    private static DatabaseManager databaseManager;
    private static CoursesDAO coursesDAO;
    private static CourseLevelsDAO coursesLevelsDAO;

    private static CoursesRepository coursesRepository;
    private static CourseLevelsRepository courseLevelsRepository;

    private static CoursesReadService coursesReadService;
    private static CoursesCreationService coursesCreationService;
    private static CourseLevelsReadService courseLevelsReadService;

    private AppContext() {
    }

    public static CoursesReadService getCoursesReadService() {
        if (coursesReadService == null) {
            coursesReadService = new CoursesReadService(getCoursesRepository());
        }
        return coursesReadService;
    }

    public static CoursesCreationService getCoursesCreationService() {
        if (coursesCreationService == null) {
            coursesCreationService = new CoursesCreationService(getCoursesRepository(), getCourseLevelsRepository());
        }
        return coursesCreationService;
    }

    public static CourseLevelsReadService getCourseLevelsReadService() {
        if (courseLevelsReadService == null) {
            courseLevelsReadService = new CourseLevelsReadService(getCourseLevelsRepository());
        }
        return courseLevelsReadService;
    }

    private static CoursesRepository getCoursesRepository() {
        if (coursesRepository == null) {
            coursesRepository = new CoursesJDBCRepository(getCoursesDAO());
        }
        return coursesRepository;
    }

    private static CourseLevelsRepository getCourseLevelsRepository() {
        if (courseLevelsRepository == null) {
            courseLevelsRepository = new CourseLevelsJDBCRepository(getCoursesLevelsDAO());
        }
        return courseLevelsRepository;
    }

    private static CoursesDAO getCoursesDAO() {
        if (coursesDAO == null) {
            coursesDAO = new CoursesDAO(getDatabaseManager(), getCoursesLevelsDAO());
        }
        return coursesDAO;
    }

    private static CourseLevelsDAO getCoursesLevelsDAO() {
        if (coursesLevelsDAO == null) {
            coursesLevelsDAO = new CourseLevelsDAO(getDatabaseManager());
        }
        return coursesLevelsDAO;
    }

    private static DatabaseManager getDatabaseManager() {
        if (databaseManager == null) {
            try {
                databaseManager = new DatabaseManager(DATABASE_PATH);
            } catch (SQLException e) {
                throw new AppInitializationException("Error initializing database", e);
            }
        }
        return databaseManager;
    }

    private static class AppInitializationException extends RuntimeException {
        AppInitializationException(String message, Exception e) {
            super(message, e);
        }
    }
}
