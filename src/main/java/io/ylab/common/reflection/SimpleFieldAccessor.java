package io.ylab.common.reflection;

import io.ylab.common.conversion.Types;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Map;


@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SimpleFieldAccessor <T> implements FieldAccessor<T> {
    private final Map<String, Object> properties;

    public static<R> SimpleFieldAccessor<R> create(R instance) {
        final PropertyExtractor<R> extractor
                = PropertyExtractor.of(Types.tClassFrom(instance));
        return create(extractor, instance);
    }

    public static <R> SimpleFieldAccessor<R> create(PropertyExtractor<R> extractor, R instance) {
        final Map<String, Object> properties = extractor
                .fieldValuesOf(instance);
        return new SimpleFieldAccessor<>(properties);
    }

    @Override
    public Object getFieldValue(String fieldName) {
        return properties.get(fieldName);
    }
}
