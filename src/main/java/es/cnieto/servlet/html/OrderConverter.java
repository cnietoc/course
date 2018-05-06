package es.cnieto.servlet.html;

import es.cnieto.domain.CourseOrder;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Collections.unmodifiableMap;
import static java.util.stream.Collectors.toMap;

public class OrderConverter {
    private final Map<OrderParameter, CourseOrder> orderMap;

    public OrderConverter() {
        orderMap = unmodifiableMap(
                Stream.of(
                        new SimpleEntry<>(OrderParameter.TITLE, CourseOrder.TITLE),
                        new SimpleEntry<>(OrderParameter.HOURS, CourseOrder.HOURS),
                        new SimpleEntry<>(OrderParameter.LEVEL, CourseOrder.LEVEL)).
                        collect(toMap(SimpleEntry::getKey, SimpleEntry::getValue)));
    }

    CourseOrder from(OrderParameter orderParameter) {
        return orderMap.get(orderParameter);
    }
}
