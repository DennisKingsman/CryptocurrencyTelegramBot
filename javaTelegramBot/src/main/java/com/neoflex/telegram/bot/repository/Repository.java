package com.neoflex.telegram.bot.repository;

import java.util.Map;

public interface Repository<T> {

    void save(T entity);

    Map<String, T> findAll();

    void update(T entity);

    void delete(String id);

    T findById(String id);

}
