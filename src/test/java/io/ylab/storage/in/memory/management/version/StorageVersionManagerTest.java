package io.ylab.storage.in.memory.management.version;

import io.ylab.storage.in.memory.management.StorageManager;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StorageVersionManagerTest {
    @Test
    void initializeStorage_shouldCallUpgradeOnVersions() {
        final StorageManager storageManager = new StorageManager();
        final StorageVersionManager versionManager = new StorageVersionManager(storageManager);
        final StorageVersion v1Version = Mockito.mock(StorageVersion.class);
        when(v1Version.getVersion()).thenReturn(1);

        versionManager
                .addVersion(v1Version)
                .initializeStorage();

        verify(v1Version).upgrade(any());
    }
}