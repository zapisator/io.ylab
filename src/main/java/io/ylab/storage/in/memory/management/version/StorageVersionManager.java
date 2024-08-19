package io.ylab.storage.in.memory.management.version;

import io.ylab.storage.in.memory.management.StorageManager;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
public class StorageVersionManager {
    private final List<StorageVersion> versions = new ArrayList<>();
    private final StorageManager storageManager;

    @Getter
    @Setter(value = AccessLevel.PRIVATE)
    private int currentVersion = 0; //  Начальная  версия

    public StorageManager initializeStorage() {
        for (StorageVersion version : versions) {
            if (version.getVersion() > currentVersion) {
                version.upgrade(storageManager);
                currentVersion = version.getVersion(); //  Обновление  текущей  версии
            }
        }
        return storageManager;
    }

    public StorageVersionManager addVersion(StorageVersion version) {
        if (versions.stream().anyMatch(v -> v.getVersion() == version.getVersion())) {
            throw new IllegalArgumentException("Версия  " + version.getVersion() + "  уже  существует");
        }
        versions.add(version);
        versions.sort(Comparator.comparingInt(StorageVersion::getVersion));
        return this;
    }
}