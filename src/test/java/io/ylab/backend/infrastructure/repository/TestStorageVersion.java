package io.ylab.backend.infrastructure.repository;

import io.ylab.storage.in.memory.management.StorageManager;
import io.ylab.storage.in.memory.management.version.StorageVersion;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class TestStorageVersion implements StorageVersion {
    protected final StorageManager storageManager;

    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public abstract void upgrade(StorageManager storageManager);
}
