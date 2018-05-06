package es.cnieto.servlet.html;

import java.util.Optional;
import java.util.stream.Stream;

public enum OrderParameter {
    TITLE("title"), HOURS("hours"), LEVEL("level");

    private final String value;

    OrderParameter(String value) {
        this.value = value;
    }

    static Optional<OrderParameter> findFrom(String parameterValue) {
        return Stream.of(OrderParameter.values())
                .filter(orderParameter -> orderParameter.value.equals(parameterValue))
                .findFirst();
    }

    public String getValue() {
        return value;
    }
}
