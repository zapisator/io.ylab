package io.ylab.storage.in.memory.management.version;

import io.ylab.storage.in.memory.management.StorageManager;

public interface StorageVersion {
    int getVersion();

    void upgrade(StorageManager storageManager);
}
