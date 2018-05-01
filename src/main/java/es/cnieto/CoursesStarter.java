package es.cnieto;

import java.util.logging.Logger;

public class CoursesStarter {
    private final static Logger LOG = Logger.getLogger("es.cnieto.CoursesStarter");

    public static void main(String[] args) throws Exception {
        CoursesServer coursesServer = new CoursesServer();
        coursesServer.start();

        LOG.info("App Initialized");
    }

}
