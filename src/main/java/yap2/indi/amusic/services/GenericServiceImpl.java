package yap2.indi.amusic.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import yap2.indi.amusic.abstractions.HasId;

public class GenericServiceImpl<T extends HasId> implements GenericService<T, Long> {
    
    protected JpaRepository<T, Long> repository;

    public GenericServiceImpl(JpaRepository<T, Long> repository) {
        this.repository = repository;
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<T> findById(Long id) {
        Optional<T> entity = repository.findById(id);
        return entity;
    }

    @Override
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    public T update(T entity) {
        if (repository.existsById(entity.getId())) {
            return repository.save(entity);
        } else {
            throw new IllegalArgumentException("Нет записи с таким идентификатором.");
        }
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}