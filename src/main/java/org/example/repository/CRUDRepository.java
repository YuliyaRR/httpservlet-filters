package org.example.repository;

import java.util.Optional;

public interface CRUDRepository<T> {

    T create(T t);

    T update(T t);

    Optional<T> getById(Long id);

    boolean deleteById(Long id);
}
