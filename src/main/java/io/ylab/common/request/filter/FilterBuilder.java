package io.ylab.common.request.filter;

import io.ylab.common.reflection.PropertyExtractor;
import io.ylab.common.reflection.SimpleFieldAccessor;
import lombok.RequiredArgsConstructor;

import java.security.InvalidParameterException;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class FilterBuilder<T> {
    private final Class<T> tClass;

    public Predicate<T> buildFilter(List<FilterCriterion<?>> criteria) {
        final PropertyExtractor<T> extractor = PropertyExtractor.of(tClass);
        final Map<String, CriterionEvaluator<?>> evaluators = evaluators(criteria);
        Predicate<T> filter = x -> true;

        for (FilterCriterion<?> criterion : criteria) {
            final String fieldName = criterion.getField();

            filter = filter.and(obj -> {
                final SimpleFieldAccessor<T> fieldAccessor = SimpleFieldAccessor.create(extractor, obj);
                final Object fieldValue = fieldAccessor.getFieldValue(fieldName);

                requireInstanceOfComparable(fieldValue);
                final CriterionEvaluator<?> evaluator = evaluators.get(fieldName);
                return evaluate(evaluator, fieldValue);
            });
        }
        return filter;
    }

    @SuppressWarnings("unchecked")
    private <R extends Comparable<? super R>> boolean evaluate(
            CriterionEvaluator<?> evaluator, Object fieldValue
    ) {
        final Class<R> rClass = (Class<R>) fieldValue.getClass();
        final R rFieldValue = rClass.cast(fieldValue);
        final CriterionEvaluator<R> rCriterionEvaluator = (CriterionEvaluator<R>) evaluator;
        return rCriterionEvaluator.evaluate(rFieldValue);
    }

    private Map<String, CriterionEvaluator<?>> evaluators(List<FilterCriterion<?>> criteria) {
        return criteria.stream()
                .map(criterion -> new AbstractMap.SimpleImmutableEntry<>(
                        criterion.getField(),
                        rCriterionEvaluatorFrom(criterion))
                )
                .collect(Collectors.toMap(
                        AbstractMap.SimpleImmutableEntry::getKey,
                        AbstractMap.SimpleImmutableEntry::getValue
                ));
    }


    @SuppressWarnings("unchecked")
    public <R extends Comparable<? super R>> CriterionEvaluator<R> rCriterionEvaluatorFrom(
            FilterCriterion<?> filterCriterion
    ) {
        requireInstanceOfComparable(filterCriterion.getValue());

        final Class<R> rClass = (Class<R>) filterCriterion.getValue().getClass();
        final FilterCriterion<R> rFilterCriterion
                = toCertainTypeFilterCriterionFrom(filterCriterion, rClass);
        return new DefaultCriterionEvaluator<>(rFilterCriterion);
    }

    private static void requireInstanceOfComparable(Object object) {
        if (!(object instanceof Comparable)) {
            throw new InvalidParameterException("Comparable value:'"
                    + object + "' is expected"
            );
        }
    }

    @SuppressWarnings("unchecked")
    public <X extends Comparable<? super X>> FilterCriterion<X> toCertainTypeFilterCriterionFrom(
            FilterCriterion<?> filterCriterion, Class<X> rClass
    ) {
        if (filterCriterion.getValue().getClass() == rClass) {
            return (FilterCriterion<X>) filterCriterion;
        }
        throw new IllegalArgumentException("filter Criterion '" + filterCriterion
                + "' is can not be converted to FilterCriterion<"
                + filterCriterion.getValue().getClass() + "'");
    }
}