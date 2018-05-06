package es.cnieto.database;

import es.cnieto.domain.CourseOrder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

class CoursesDAOOrderConverterTest {
    private CoursesDAOOrderConverter coursesDAOOrderConverter = new CoursesDAOOrderConverter();

    @Test
    void convertWhenIsTitle() {
        CoursesDAO.Order order = coursesDAOOrderConverter.from(CourseOrder.TITLE);

        assertSame(CoursesDAO.Order.TITLE, order);
    }

    @Test
    void convertWhenIsHours() {
        CoursesDAO.Order order = coursesDAOOrderConverter.from(CourseOrder.HOURS);

        assertSame(CoursesDAO.Order.HOURS, order);
    }

    @Test
    void convertWhenIsLevel() {
        CoursesDAO.Order order = coursesDAOOrderConverter.from(CourseOrder.LEVEL);

        assertSame(CoursesDAO.Order.LEVEL, order);
    }
}