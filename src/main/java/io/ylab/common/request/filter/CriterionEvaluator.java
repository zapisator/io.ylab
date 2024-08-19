package io.ylab.common.request.filter;

public interface CriterionEvaluator<T extends Comparable<? super T>> {
    boolean evaluate(T fieldValue);
}
