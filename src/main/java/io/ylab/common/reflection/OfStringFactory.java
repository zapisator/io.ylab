package io.ylab.common.reflection;

import lombok.RequiredArgsConstructor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Optional;

@RequiredArgsConstructor
public class OfStringFactory<T> {
    private final Class<T> tClass;
    private final String value;

    public Optional<T> create() {
        Optional<T> instance = createFromConstructor();
        if (!instance.isPresent()) {
            instance = createFromFactoryMethod();
        }
        return instance;
    }

    private Optional<T> createFromConstructor() {
        try {
            final Constructor<T> constructor = findStringConstructor();
            if (constructor != null) {
                return Optional.of(constructor.newInstance(value));
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            // then empty result
        }
        return Optional.empty();
    }

    @SuppressWarnings("unchecked")
    private Optional<T> createFromFactoryMethod() {
        try {
            Method method = findStringFactoryMethod();
            if (method != null) {
                return Optional.of((T) method.invoke(null, value));
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            // then empty result
        }
        return Optional.empty();
    }

    private Constructor<T> findStringConstructor() {
        try {
            Constructor<T> constructor = tClass.getConstructor(String.class);
            if (Modifier.isPublic(constructor.getModifiers())) {
                return constructor;
            }
        } catch (NoSuchMethodException e) {
            // Конструктор не найден
        }
        return null;
    }

    private Method findStringFactoryMethod() {
        final Method[] methods = tClass.getDeclaredMethods();
        Method fabricMethod = null;

        for (int i = 0; (i < methods.length) && (fabricMethod == null); i++) {
            final Method method = methods[i];

            if (isHasPublicFabricParsingMethod(method)) {
                fabricMethod = method;
            }
        }
        return fabricMethod;
    }

    private boolean isHasPublicFabricParsingMethod(Method method) {
        return Modifier.isStatic(method.getModifiers())
                && Modifier.isPublic(method.getModifiers())
                && method.getParameterCount() == 1
                && CharSequence.class.isAssignableFrom(method.getParameterTypes()[0])
                && method.getReturnType() == tClass;
    }
}
