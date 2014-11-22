package com.nesterione.oop.storage;

import java.util.List;
import java.util.UUID;

/**
 * Created by igor on 22.11.2014.
 */
public interface Storage<T extends Phone> {
    List<T> getAll();
    T get(UUID id);
    void add(T entity);
    void delete(T entity);
    void update(T entity);
}
