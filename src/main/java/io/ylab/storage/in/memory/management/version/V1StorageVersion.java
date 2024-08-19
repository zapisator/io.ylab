package io.ylab.storage.in.memory.management.version;

import io.ylab.storage.in.memory.management.StorageManager;
import io.ylab.storage.in.memory.storage.InMemoryCarStorage;
import io.ylab.storage.in.memory.storage.InMemoryLogStorage;
import io.ylab.storage.in.memory.storage.InMemoryOrderStorage;
import io.ylab.storage.in.memory.storage.InMemoryUserStorage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class V1StorageVersion implements StorageVersion {
    private final StorageManager storageManager;

    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public void upgrade(StorageManager storageManager) {
        storageManager.addStorage("Car", new InMemoryCarStorage());
        storageManager.addStorage("User", new InMemoryUserStorage());
        storageManager.addStorage("Order", new InMemoryOrderStorage());
        storageManager.addStorage("Log", new InMemoryLogStorage());
    }
}
