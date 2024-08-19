package io.ylab.common.request.filter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@RequiredArgsConstructor
@Getter
@Accessors(fluent = true)
public enum ComparisonOperator {
    EQUALS("eq"),
    NOT_EQUALS("ne"),
    GREATER("gt"),
    NOT_LESS("ge"),
    LESS("lt"),
    NOT_GREATER("le");

    private final String value;

    public static ComparisonOperator fromValue(String value) {
        for (ComparisonOperator operator : ComparisonOperator.values()) {
            if (operator.value().equals(value)) {
                return operator;
            }
        }
        throw new IllegalArgumentException("Некорректное  значение  оператора:  " + value);
    }
}
