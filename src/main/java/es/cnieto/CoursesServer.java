package es.cnieto;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

import java.util.logging.Logger;

public class CoursesServer {
    private final static Logger LOG = Logger.getLogger("es.cnieto.CoursesServer");

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        createServlet(server);

        server.start();

        LOG.info("Started");
    }

    private static void createServlet(Server server) {
        ServletContextHandler servletContextHandler = new ServletContextHandler(server, "/", true, false);
        servletContextHandler.addServlet(CoursesServlet.class, "/");
    }
}
