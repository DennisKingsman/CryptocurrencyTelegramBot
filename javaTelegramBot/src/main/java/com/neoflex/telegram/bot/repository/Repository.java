package com.neoflex.telegram.bot.repository;

import java.util.Map;

public interface Repository<T> {

    void save(T entity);

    default Map<String, T> findAll() {
        return null;
    }

    void update(T entity);

    default void delete(String id) {

    }

    default T findById(String id) {
        return null;
    }

}
