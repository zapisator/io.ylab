package io.ylab.storage.in.memory.management;

import io.ylab.storage.in.memory.storage.Storage;

import java.util.HashMap;
import java.util.Map;

public class StorageManager {
    private final Map<String, Storage<?, ?>> storages = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T, I> Storage<T, I> getStorage(String entityName) {
        return (Storage<T, I>) storages.get(entityName);
    }

    public <T, I> void addStorage(String entityName, Storage<T, I> storage) {
        storages.put(entityName, storage);
    }
}
