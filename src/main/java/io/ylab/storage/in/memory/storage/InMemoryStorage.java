package io.ylab.storage.in.memory.storage;

import io.ylab.backend.domain.service.dto.Pagination;
import io.ylab.backend.domain.service.dto.Sorting;
import io.ylab.storage.in.memory.data.Identifiable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public abstract class InMemoryStorage<T extends Identifiable<I>, I extends Comparable<? super I>>
        implements Storage<T, I> {
    private final Map<I, T> entities = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public List<T> findAll() {
        return new ArrayList<>(entities.values());
    }

    @Override
    public Optional<T> findById(I i) {
        return Optional.ofNullable(entities.get(i));
    }

    @Override
    public I save(T entity) {
        if (entity.getId() == null) {
            entity.setId(generateNextId());
        } else {
            if (entities.containsKey(entity.getId())) {
                try {
                    T updatedEntity = copyProperties(entity);
                    entities.put(entity.getId(), updatedEntity);
                    return entity.getId();
                } catch (Exception e) {
                    throw new RuntimeException("Ошибка  при  обновлении  сущности:  " + e.getMessage());
                }
            }
        }
        entities.put(entity.getId(), entity);
        return entity.getId();
    }

    @SuppressWarnings("unchecked")
    private T copyProperties(T source) throws Exception {
        final Class<?> clazz = source.getClass();
        final Object builder = clazz.getMethod("builder").invoke(null);

        for (Field field : clazz.getDeclaredFields()) {
            final String fieldName = field.getName();
            final String getterName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            final Method getter = clazz.getMethod(getterName);
            final Method setter = builder.getClass().getMethod(fieldName, getter.getReturnType());
            final Object value = getter.invoke(source);

            setter.invoke(builder, value);
        }
        return (T) builder.getClass().getMethod("build").invoke(builder);
    }

    @Override
    public List<T> findAll(Sorting sorting) {
        List<T> result = findAll();

        return sorted(result, sorting);
    }

    @Override
    public List<T> findAll(Pagination pagination) {
        List<T> result = findAll();

        if (pagination != null) {
            final int startIndex = (pagination.getPage() - 1) * pagination.getPageSize();
            final int endIndex = Math.min(startIndex + pagination.getPageSize(), result.size());
            result = result.subList(startIndex, endIndex);
        }
        return result;
    }


    @Override
    public List<T> findAll(Sorting sorting, Pagination pagination) {
        List<T> result = findAll();

        result = sorted(result, sorting);
        result = paginated(result, pagination);
        return result;
    }

    protected List<T> paginated(List<T> cars, Pagination pagination) {
        if (pagination != null) {
            final int startIndex = (pagination.getPage() - 1) * pagination.getPageSize();
            final int endIndex = Math.min(startIndex + pagination.getPageSize(), cars.size());
            cars = cars.subList(startIndex, endIndex);
        }
        return cars;
    }

    protected List<T> sorted(List<T> cars, Sorting sorting) {
        return sorting == null
                ? cars
                : cars.stream()
                        .sorted(createComparator(sorting))
                        .collect(Collectors.toList());
    }

    protected abstract Comparator<T> createComparator(Sorting sorting);

    @Override
    public void deleteById(I i) {
        entities.remove(i);
    }

    private I generateNextId() {
        return (I) (Long) idGenerator.getAndIncrement();
    }
}
