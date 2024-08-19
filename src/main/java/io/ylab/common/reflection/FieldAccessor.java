package io.ylab.common.reflection;

public interface FieldAccessor<T> {
    Object getFieldValue(String fieldName);
}
