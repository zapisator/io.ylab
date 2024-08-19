package io.ylab.common.request.filter;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class DefaultCriterionEvaluator<T extends Comparable<? super T>>
        implements CriterionEvaluator<T> {
    private final FilterCriterion<T> criterion;

    @Override
    public boolean evaluate(T fieldValue) {
        if (fieldValue == null) {
            return false;
        }

        return compare(fieldValue, criterion.getValue(), criterion.getOperator());
    }

    private boolean compare(T a, T b, ComparisonOperator operator) {
        if (operator == null) {
            throw new IllegalArgumentException("Неверный аргумент '" + operator + "'");
        }

        final int comparison = a.compareTo(b);
        boolean result = true;

        switch (operator) {
            case EQUALS:
                result = comparison == 0;
                break;
            case NOT_EQUALS:
                result = comparison != 0;
                break;
            case GREATER:
                result = comparison > 0;
                break;
            case NOT_LESS:
                result = comparison >= 0;
                break;
            case LESS:
                result = comparison < 0;
                break;
            case NOT_GREATER:
                result = comparison <= 0;
                break;
        }
        return result;
    }

}
