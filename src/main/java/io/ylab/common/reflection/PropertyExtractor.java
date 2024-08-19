package io.ylab.common.reflection;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PropertyExtractor<T> {
    private final Class<T> tClass;
    private final Map<String, Method> properties = new HashMap<>();

    public static <R> PropertyExtractor<R> of(Class<R> tClass) {
        if (tClass.isInterface()) {
            throw new IllegalArgumentException("Переданный класс должен быть классом, а не интерфейсом.");
        }
        final PropertyExtractor<R> extractor = new PropertyExtractor<>(tClass);

        extractor.extractProperties();
        return extractor;
    }

    public Map<String, Object> fieldValuesOf(T instance) {
        return properties.entrySet().stream()
                .map(pair -> new AbstractMap.SimpleImmutableEntry<>(
                        pair.getKey(),
                        invokeGetter(instance, pair.getValue())
                ))
                .collect(Collectors.toMap(
                        AbstractMap.SimpleImmutableEntry::getKey,
                        AbstractMap.SimpleImmutableEntry::getValue
                ));
    }

    private void extractProperties() {
        final Set<String> fieldNames = extractFieldNames(tClass);
        Arrays.stream(tClass.getDeclaredMethods())
                .filter(this::isSupposedGetter)
                .filter(method -> isMatchesWithFieldName(fieldNames, method.getName()))
                .map(method -> new AbstractMap.SimpleImmutableEntry<>(
                        toFluentGetterName(method.getName()), method))
                .filter(pair -> pair.getValue() != null)
                .forEach(pair -> properties.put(pair.getKey(), pair.getValue()));
    }

    private Object invokeGetter(T instance, Method method) {
        try {
            return method.invoke(instance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<String> extractFieldNames(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .map(Field::getName)
                .collect(Collectors.toSet());
    }

    private boolean isSupposedGetter(Method method) {
        return !Modifier.isStatic(method.getModifiers())
                && Modifier.isPublic(method.getModifiers())
                && method.getParameterCount() == 0
                && !method.getReturnType().equals(Void.class);
    }

    private boolean isMatchesWithFieldName(Set<String> fieldNames, String methodName) {
        return fieldNames.contains(methodName)
                || fieldNames.contains(toFluentGetterName(methodName));
    }

    private String toFluentGetterName(String getterName) {
        if (getterName.length() > 3 && getterName.startsWith("get")) {
            return Character.toLowerCase(getterName.charAt(3))
                    + (getterName.length() == 4
                    ? ""
                    : getterName.substring(4)
            );
        }
        return getterName;
    }

}

