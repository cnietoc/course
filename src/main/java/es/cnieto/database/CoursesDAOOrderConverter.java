package es.cnieto.database;

import es.cnieto.domain.CourseOrder;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Collections.unmodifiableMap;
import static java.util.stream.Collectors.toMap;

public class CoursesDAOOrderConverter {
    private final Map<CourseOrder, CoursesDAO.Order> orderMap;

    public CoursesDAOOrderConverter() {
        orderMap = unmodifiableMap(
                Stream.of(
                        new AbstractMap.SimpleEntry<>(CourseOrder.TITLE, CoursesDAO.Order.TITLE),
                        new AbstractMap.SimpleEntry<>(CourseOrder.HOURS, CoursesDAO.Order.HOURS),
                        new AbstractMap.SimpleEntry<>(CourseOrder.LEVEL, CoursesDAO.Order.LEVEL)).
                        collect(toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue)));
    }

    CoursesDAO.Order from(CourseOrder courseOrder) {
        return orderMap.get(courseOrder);
    }
}
