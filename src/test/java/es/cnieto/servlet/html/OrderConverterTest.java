package es.cnieto.servlet.html;

import es.cnieto.domain.CourseOrder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

class OrderConverterTest {
    private OrderConverter orderConverter = new OrderConverter();

    @Test
    void convertWhenIsTitle() {
        CourseOrder order = orderConverter.from(OrderParameter.TITLE);

        assertSame(CourseOrder.TITLE, order);
    }

    @Test
    void convertWhenIsHours() {
        CourseOrder order = orderConverter.from(OrderParameter.HOURS);

        assertSame(CourseOrder.HOURS, order);
    }

    @Test
    void convertWhenIsLevel() {
        CourseOrder order = orderConverter.from(OrderParameter.LEVEL);

        assertSame(CourseOrder.LEVEL, order);
    }
}