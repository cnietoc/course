# Decisions
- Use Jetty as embedded server without use Maven plugin to run it.
- Use java.util.log as logging system because it is on J2SE and configuring Slf4j (used by Jetty) to use this implementation.
- Using derby database database for persistence, on disk for production environment and on memory for testing.
- Custom implementation for IoC (see AppContext).
# Improvements
- Use a pool for database connection instead of open/close on each operation.