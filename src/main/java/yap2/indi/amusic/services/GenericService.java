package yap2.indi.amusic.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import yap2.indi.amusic.abstractions.HasId;

@Service
public interface GenericService<T extends HasId, Id> {
    List<T> findAll();
    Optional<T> findById(Id id);
    T save(T entity);
    T update(T entity);
    void delete(Id id);
} 