package io.ylab.backend.infrastructure.repository.search;

import io.ylab.backend.domain.service.search.ReflectiveSpecification;
import io.ylab.common.request.filter.FilterCriterion;
import lombok.Value;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static io.ylab.common.request.filter.ComparisonOperator.EQUALS;
import static io.ylab.common.request.filter.ComparisonOperator.GREATER;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReflectiveSpecificationTest {

    @Test
    void apply_shouldFilterEntitiesBasedOnCriteria() {
        final TestObject obj1 = new TestObject("John Doe", 30, true);
        final TestObject obj2 = new TestObject("Jane Doe", 25, false);
        final TestObject obj3 = new TestObject("Peter Pan", 40, true);
        final List<TestObject> entities = Arrays.asList(obj1, obj2, obj3);

        final List<FilterCriterion<?>> criteria = Arrays.asList(
                new FilterCriterion<>("age", GREATER, 26),
                new FilterCriterion<>("isActive", EQUALS, true)
        );
        final ReflectiveSpecification<TestObject> specification = ReflectiveSpecification
                .of(TestObject.class, criteria);
        final List<TestObject> filteredEntities = specification.apply(entities);

        assertAll(
                () -> assertEquals(2, filteredEntities.size()),
                () -> assertTrue(filteredEntities.contains(obj1)),
                () -> assertFalse(filteredEntities.contains(obj2)),
                () -> assertTrue(filteredEntities.contains(obj3))
        );
    }

    // ... (другие  тестовые  случаи) ...

    @Value
    public static class TestObject {
        String name;
        int age;
        boolean isActive;
    }

}