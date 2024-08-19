package io.ylab.common.request;

import io.ylab.common.request.filter.FilterCriterion;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.ylab.common.request.SortOrder.ASC;
import static io.ylab.common.request.filter.ComparisonOperator.EQUALS;
import static io.ylab.common.request.filter.ComparisonOperator.GREATER;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RequestParamsTest {
    @Test
    void fromQueryString_validSearchParams_shouldReturnCorrectTypedCriteria() {
        final Map<String, Class<?>> fieldTypes = Stream.of(
                        new AbstractMap.SimpleImmutableEntry<String, Class<?>>("name", String.class),
                        new AbstractMap.SimpleImmutableEntry<String, Class<?>>("age", Integer.class),
                        new AbstractMap.SimpleImmutableEntry<String, Class<?>>("date", LocalDate.class)
                )
                .collect(Collectors.toMap(
                        AbstractMap.SimpleImmutableEntry::getKey,
                        AbstractMap.SimpleImmutableEntry::getValue
                ));
        final String queryString = "page=1&size=10&search=name:eq:John Doe;age:gt:25;date:eq:2023-12-28";

        final RequestParams params = RequestParams.fromQueryString(queryString, fieldTypes);
        final List<FilterCriterion<?>> nameCriteria = params.searchParams().get("name");
        final List<FilterCriterion<?>> ageCriteria = params.searchParams().get("age");
        final List<FilterCriterion<?>> dateCriteria = params.searchParams().get("date");

        assertAll(
                () -> assertEquals(1, nameCriteria.size()),
                () -> assertEquals("name", nameCriteria.get(0).getField()),
                () -> assertEquals(EQUALS, nameCriteria.get(0).getOperator()),
                () -> assertEquals("John Doe", nameCriteria.get(0).getValue()),
                () -> assertInstanceOf(String.class, nameCriteria.get(0).getValue()),

                () -> assertEquals(1, ageCriteria.size()),
                () -> assertEquals("age", ageCriteria.get(0).getField()),
                () -> assertEquals(GREATER, ageCriteria.get(0).getOperator()),
                () -> assertEquals(25, ageCriteria.get(0).getValue()),
                () -> assertInstanceOf(Integer.class, ageCriteria.get(0).getValue()),
//
                () -> assertEquals(1, dateCriteria.size()),
                () -> assertEquals("date", dateCriteria.get(0).getField()),
                () -> assertEquals(EQUALS, dateCriteria.get(0).getOperator()),
                () -> assertEquals(LocalDate.of(2023, 12, 28), dateCriteria.get(0).getValue()),
                () -> assertInstanceOf(LocalDate.class, dateCriteria.get(0).getValue())
        );
    }

    @Test
    void fromQueryString_invalidSearchParamType_shouldThrowIllegalArgumentException() {
        final String queryString = "search=age:eq:abc";
        final Map<String, Class<?>> fieldTypes = new HashMap<>();

        fieldTypes.put("age", Integer.class);

        assertThrows(IllegalArgumentException.class, () -> RequestParams.fromQueryString(queryString, fieldTypes));
    }

    @Test
    void fromQueryString_emptyQueryString_shouldReturnDefaultValues() {
        final Map<String, Class<?>> fieldTypes = new HashMap<>();
        final String queryString = "";
        final RequestParams params = RequestParams.fromQueryString(queryString, fieldTypes);

        assertAll(
                () -> assertEquals(1, params.page()),
                () -> assertEquals(10, params.pageSize()),
                () -> assertEquals("id", params.sortBy()),
                () -> assertEquals(ASC.key(), params.sortOrder()),
                () -> assertTrue(params.searchParams().isEmpty())
        );
    }

    @Test
    void fromQueryString_nullQueryString_shouldReturnDefaultValues() {
        final Map<String, Class<?>> fieldTypes = new HashMap<>();
        final RequestParams params = RequestParams.fromQueryString(null, fieldTypes);

        assertAll(
                () -> assertEquals(1, params.page()),
                () -> assertEquals(10, params.pageSize()),
                () -> assertEquals("id", params.sortBy()),
                () -> assertEquals(ASC.key(), params.sortOrder()),
                () -> assertTrue(params.searchParams().isEmpty())
        );    }
}