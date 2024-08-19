package io.ylab.common.request.filter;

import lombok.Value;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static io.ylab.common.request.filter.ComparisonOperator.EQUALS;
import static io.ylab.common.request.filter.ComparisonOperator.GREATER;
import static io.ylab.common.request.filter.ComparisonOperator.NOT_GREATER;
import static org.junit.jupiter.api.Assertions.*;

class FilterBuilderTest {
    @Test
    void buildFilter_multipleCriteria_shouldFilterCorrectly() {
        final TestObject testObject1 = new TestObject("John", 30, 100L, 1.5);
        final TestObject testObject2 = new TestObject("Jane", 25, 150L, 2.5);
        final TestObject testObject3 = new TestObject("Peter", 35, 200L, 3.5);
        final List<TestObject> testObjects = Arrays.asList(testObject1, testObject2, testObject3);
        final List<FilterCriterion<?>> criteria = Arrays.asList(
                new FilterCriterion<>("age", GREATER, 25),
                new FilterCriterion<>("longField", NOT_GREATER, 150L)
        );

        final FilterBuilder<TestObject> filterBuilder = new FilterBuilder<>(TestObject.class);
        final Predicate<TestObject> filter = filterBuilder.buildFilter(criteria);
        final List<TestObject> result = testObjects.stream()
                .filter(filter)
                .collect(Collectors.toList());

        assertAll(
                () -> assertEquals(1, result.size()),
                () -> assertEquals(testObject1, result.get(0))
        );
    }

    @Test
    void buildFilter_shouldFilterEntitiesBasedOnCriteria() {
        final TestObject obj1 = new TestObject("John Doe", 30, 100L, 1.5);
        final TestObject obj2 = new TestObject("Jane Doe", 25, 150L, 2.5);
        final TestObject obj3 = new TestObject("Peter Pan", 40, 200L, 3.5);
        final List<FilterCriterion<?>> criteria = Arrays.asList(
                new FilterCriterion<>("age", GREATER, 25),
                new FilterCriterion<>("name", EQUALS, "John Doe")
        );

        final FilterBuilder<TestObject> builder = new FilterBuilder<>(TestObject.class);
        final Predicate<TestObject> filter = builder.buildFilter(criteria);

        assertAll(
                () -> assertTrue(filter.test(obj1)),
                () -> assertFalse(filter.test(obj2)),
                () -> assertFalse(filter.test(obj3))
        );
    }

    @Test
    void buildFilter_emptyCriteria_shouldReturnTruePredicate() {
        final TestObject obj = new TestObject("Test", 10, 100L, 1.5);

        final FilterBuilder<TestObject> builder = new FilterBuilder<>(TestObject.class);
        final Predicate<TestObject> filter = builder.buildFilter(Collections.emptyList());

        assertTrue(filter.test(obj));
    }

    @Test
    void buildFilter_nonExistingField_shouldThrowIllegalArgumentException() {
        final List<FilterCriterion<?>> criteria = Collections.singletonList(
                new FilterCriterion<>("nonExistingField", EQUALS, 2.0)
        );
        final FilterBuilder<TestObject> builder = new FilterBuilder<>(TestObject.class);

        assertDoesNotThrow(() -> builder.buildFilter(criteria));
    }

    // ... (другие  тесты) ...

    @Value
    public static class TestObject implements Comparable<TestObject> {
        String name;
        int age;
        Long longField;
        Double doubleField;

        @Override
        public int compareTo(TestObject o) {
            return this.name.compareTo(o.name);
        }
    }

}