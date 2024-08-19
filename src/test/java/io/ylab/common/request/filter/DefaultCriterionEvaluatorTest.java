package io.ylab.common.request.filter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static io.ylab.common.request.filter.ComparisonOperator.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class DefaultCriterionEvaluatorTest {

    @ParameterizedTest
    @MethodSource("stringComparisonDataProvider")
    void evaluate_stringComparison_shouldReturnCorrectResult(
            ComparisonOperator operator, String fieldValue,
            String criterionValue, boolean expectedResult) {
        final FilterCriterion<String> criterion
                = new FilterCriterion<>("name", operator, criterionValue);
        final CriterionEvaluator<String> evaluator
                = new DefaultCriterionEvaluator<>(criterion);

        final boolean result = evaluator.evaluate(fieldValue);

        assertEquals(expectedResult, result);
    }

    @ParameterizedTest
    @MethodSource("localDateComparisonDataProvider")
    void evaluate_localDateComparison_shouldReturnCorrectResult(
            ComparisonOperator operator, LocalDate fieldValue,
            LocalDate criterionValue, boolean expectedResult
    ) {
        final FilterCriterion<LocalDate> criterion
                = new FilterCriterion<>("date", operator, criterionValue);
        final DefaultCriterionEvaluator<LocalDate> evaluator
                = new DefaultCriterionEvaluator<>(criterion);

        final boolean result = evaluator.evaluate(fieldValue);

        assertEquals(expectedResult, result);
    }

    @ParameterizedTest
    @MethodSource("integerComparisonDataProvider")
    void evaluate_integerComparison_shouldReturnCorrectResult(
            ComparisonOperator operator, int fieldValue,
            int criterionValue, boolean expectedResult
    ) {
        final FilterCriterion<Integer> criterion
                = new FilterCriterion<>("age", operator, criterionValue);
        final DefaultCriterionEvaluator<Integer> evaluator
                = new DefaultCriterionEvaluator<>(criterion);

        final boolean result = evaluator.evaluate(fieldValue);

        assertEquals(expectedResult, result);
    }

    @ParameterizedTest
    @MethodSource("longComparisonDataProvider")
    void evaluate_longComparison_shouldReturnCorrectResult(
            ComparisonOperator operator, long fieldValue,
            Long criterionValue, boolean expectedResult
    ) {
        final FilterCriterion<Long> criterion
                = new FilterCriterion<>("longField", operator, criterionValue);
        final DefaultCriterionEvaluator<Long> evaluator
                = new DefaultCriterionEvaluator<>(criterion);

        final boolean result = evaluator.evaluate(fieldValue);

        assertEquals(expectedResult, result);
    }

    @ParameterizedTest
    @MethodSource("doubleComparisonDataProvider")
    void evaluate_doubleComparison_shouldReturnCorrectResult(
            ComparisonOperator operator, double fieldValue,
            double criterionValue, boolean expectedResult
    ) {
        final FilterCriterion<Double> criterion
                = new FilterCriterion<>("doubleField", operator, criterionValue);
        final DefaultCriterionEvaluator<Double> evaluator
                = new DefaultCriterionEvaluator<>(criterion);

        final boolean result = evaluator.evaluate(fieldValue);

        assertEquals(expectedResult, result);
    }

    @Test
    void evaluate_nullFieldValue_shouldReturnFalse() {
        final FilterCriterion<String> criterion = new FilterCriterion<>("name", EQUALS, "John");
        final DefaultCriterionEvaluator<String> evaluator = new DefaultCriterionEvaluator<>(criterion);

        final boolean result = evaluator.evaluate(null);

        assertFalse(result);
    }

    @Test
    void evaluate_unsupportedOperator_shouldThrowIllegalArgumentException() {
        final FilterCriterion<Integer> criterion = new FilterCriterion<>("age", null, 25);
        final DefaultCriterionEvaluator<Integer> evaluator = new DefaultCriterionEvaluator<>(criterion);

        assertThrows(IllegalArgumentException.class, () -> evaluator.evaluate(30));
    }

    static Stream<Arguments> stringComparisonDataProvider() {
        return Stream.of(
                arguments(EQUALS, "John", "John", true),
                arguments(EQUALS, "john", "John", false),
                arguments(NOT_EQUALS, "john", "John", true),
                arguments(GREATER, "a", "b", false),
                arguments(LESS, "a", "b", true),
                arguments(NOT_GREATER, "aa", "ab", true),
                arguments(NOT_LESS, "aa", "ab", false)
        );
    }

    static Stream<Arguments> localDateComparisonDataProvider() {
        final LocalDate date1 = LocalDate.of(2023, 12, 20);
        final LocalDate date2 = LocalDate.of(2024, 1, 15);
        return Stream.of(
                arguments(EQUALS, date1, date1, true),
                arguments(GREATER, date2, date1, true),
                arguments(LESS, date1, date2, true),
                arguments(NOT_LESS, date1, date1, true),
                arguments(NOT_GREATER, date2, date2, true),
                arguments(NOT_EQUALS, date1, date2, true),
                arguments(EQUALS, date1, date2, false)
        );
    }

    static Stream<Arguments> integerComparisonDataProvider() {
        return Stream.of(
                arguments(EQUALS, 30, 30, true),
                arguments(GREATER, 30, 25, true),
                arguments(LESS, 25, 30, true),
                arguments(NOT_LESS, 30, 30, true),
                arguments(NOT_GREATER, 25, 25, true),
                arguments(NOT_EQUALS, 30, 25, true),
                arguments(EQUALS, 30, 25, false)
        );
    }

    static Stream<Arguments> longComparisonDataProvider() {
        return Stream.of(
                arguments(EQUALS, 1234567890L, 1234567890L, true),
                arguments(GREATER, 1234567890L, 1234567889L, true),
                arguments(LESS, 1234567889L, 1234567890L, true),
                arguments(NOT_LESS, 1234567890L, 1234567890L, true),
                arguments(NOT_GREATER, 1234567889L, 1234567889L, true),
                arguments(NOT_EQUALS, 1234567890L, 1234567889L, true),
                arguments(EQUALS, 1234567890L, 1234567889L, false)
        );
    }

    static Stream<Arguments> doubleComparisonDataProvider() {
        return Stream.of(
                arguments(EQUALS, 123.45, 123.45, true),
                arguments(GREATER, 123.45, 123.44, true),
                arguments(LESS, 123.44, 123.45, true),
                arguments(NOT_LESS, 123.45, 123.45, true),
                arguments(NOT_GREATER, 123.44, 123.44, true),
                arguments(NOT_EQUALS, 123.45, 123.44, true),
                arguments(EQUALS, 123.45, 123.44, false)
        );
    }
}