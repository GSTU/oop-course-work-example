package com.nesterione.oop.storage;

/**
 * Created by igor on 22.11.2014.
 */
public class StorageFactory {

    private StorageFactory() {
        // Это статический класс
    }

    private static Storage storage;

    //По умолчанию фабрика возращает репозиторий в памяти
    static {
        storage = new MemoryStorage();
    }

    public static void setInstance(Storage _storage) {
        storage = _storage;
    }

    public static Storage getInstance() {
        return storage;
    }
}
