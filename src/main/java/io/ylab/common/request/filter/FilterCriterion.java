package io.ylab.common.request.filter;

import lombok.Value;

@Value
public class FilterCriterion<T> {
    String field;
    ComparisonOperator operator;
    T value;
}
