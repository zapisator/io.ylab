package io.ylab.common.request;

import io.ylab.common.reflection.OfStringFactory;
import io.ylab.common.request.filter.ComparisonOperator;
import io.ylab.common.request.filter.FilterCriterion;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.sun.jmx.mbeanserver.Util.cast;
import static io.ylab.common.request.RequestParamKey.PAGE;
import static io.ylab.common.request.RequestParamKey.PAGE_SIZE;
import static io.ylab.common.request.RequestParamKey.SEARCH;
import static io.ylab.common.request.RequestParamKey.SORT_BY;
import static io.ylab.common.request.RequestParamKey.SORT_ORDER;
import static io.ylab.common.request.SortOrder.ASC;
import static io.ylab.common.request.SortOrder.DESC;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Accessors(fluent = true)
public class RequestParams {
    private final int page;
    private final int pageSize;
    private final String sortBy;
    private final String sortOrder;
    private final Map<String, List<FilterCriterion<?>>> searchParams;

    public static RequestParams fromQueryString(
            String queryString, Map<String, Class<?>> fieldTypes
    ) {
        final Map<String, String> params = parseQueryString(queryString);
        final int page = getIntParam(params, PAGE.key(), 1);
        final int pageSize = getIntParam(params, PAGE_SIZE.key(), 10);
        final String sortBy = getStringParam(params, SORT_BY.key(), "id");
        final String sortOrder = getStringParam(params, SORT_ORDER.key(), ASC.key())
                .equalsIgnoreCase(DESC.key())
                ? DESC.key()
                : ASC.key();
        final Map<String, List<FilterCriterion<?>>> searchParams
                = params.containsKey(SEARCH.key())
                ? parseSearchParams(params.get(SEARCH.key()), fieldTypes)
                : Collections.emptyMap();
        return new RequestParams(page, pageSize, sortBy, sortOrder, searchParams);
    }

    private static Map<String, List<FilterCriterion<?>>> parseSearchParams(
            String searchString, Map<String, Class<?>> fieldTypes
    ) {
        if (searchString == null || searchString.isEmpty()) {
            return Collections.emptyMap();
        }
        final String decodedSearchString = decodedString(searchString);
        final Map<String, List<FilterCriterion<?>>> searchParams = new HashMap<>();
        final String[] criteria = decodedSearchString.split(";");

        for (String criterion : criteria) {
            final String[] parts = criterion.split(":");
            if (parts.length == 3) {
                createAndPutFilterCriterion(parts, fieldTypes, searchParams);
            }
        }
        return searchParams;
    }

    private static <T> void createAndPutFilterCriterion(
            String[] parts, Map<String, Class<?>> fieldTypes,
            Map<String, List<FilterCriterion<?>>> searchParams
    ) {
        final String field = parts[0];
        final ComparisonOperator operator = toOperator(parts[1].trim());
        final T value = toValue(field, parts[2].trim(), fieldTypes);
        final FilterCriterion<T> filterCriterion = new FilterCriterion<>(field, operator, value);

        searchParams
                .computeIfAbsent(field, k -> new ArrayList<>())
                .add(filterCriterion);
    }

    private static <T> T toValue(String field, String value, Map<String, Class<?>> fieldTypes) {
        final Class<?> fieldType = Optional
                .ofNullable(fieldTypes.get(field))
                .orElseThrow(() -> new IllegalArgumentException("Неизвестное  поле:  " + field));

        return new OfStringFactory<T>(cast(fieldType), value)
                .create()
                .orElseThrow(
                        () -> new IllegalArgumentException("Невозможно  преобразовать  значение  '" + value + "'  в  тип  " + fieldType)
                );
    }

    private static ComparisonOperator toOperator(String operator) {
        return ComparisonOperator.fromValue(operator);
    }

    private static String decodedString(String searchString) {
        try {
            return URLDecoder.decode(searchString, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Ошибка  декодирования  search  параметра:  "
                    + e.getMessage());
        }
    }

    private static Map<String, String> parseQueryString(String queryString) {
        if (queryString == null || queryString.isEmpty()) {
            return Collections.emptyMap();
        }

        final Map<String, String> params = new HashMap<>();
        final String[] keyValuePairs = queryString.split("&");

        for (String keyValuePair : keyValuePairs) {
            final String[] parts = keyValuePair.split("=");
            if (parts.length == 2) {
                params.put(parts[0], parts[1]);
            }
        }

        return params;
    }

    private static String getStringParam(Map<String, String> params, String key, String defaultValue) {
        return params.getOrDefault(key, defaultValue);
    }

    private static int getIntParam(Map<String, String> params, String key, int defaultValue) {
        try {
            final int value = Integer.parseInt(
                    params.getOrDefault(key, String.valueOf(defaultValue))
            );
            return value < 0 ? defaultValue : value;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}