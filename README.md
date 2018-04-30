# Decisions
-  Use Jetty as embedded server without use Maven plugin to run it.
- Use java.util.log as logging system because it is on J2SE and configuring Slf4j (used by Jetty) to use this implementation