package io.ylab.storage.in.memory.management;

import io.ylab.storage.in.memory.storage.InMemoryCarStorage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StorageManagerTest {
    @Test
    void addStorage_shouldAddStorageToMap() {
        final StorageManager storageManager = new StorageManager();
        final InMemoryCarStorage carStorage = new InMemoryCarStorage();

        storageManager.addStorage("Car", carStorage);

        assertEquals(carStorage, storageManager.getStorage("Car"));
    }
}