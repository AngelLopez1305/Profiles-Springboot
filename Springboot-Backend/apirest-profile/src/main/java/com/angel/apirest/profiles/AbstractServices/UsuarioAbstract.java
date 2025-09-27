package com.angel.apirest.profiles.AbstractServices;

import java.util.stream.Collectors;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public abstract class UsuarioAbstract<E, D> {

    public abstract D entityToDTO(E entity);

    public abstract E dtoToEntity(D dto);

    public abstract E updateEntity(E existingEntity, D dto);

    // Metodo Abstracto para crear
    public D create(D dto, JpaRepository<E, Long> repository) {
        E entity = dtoToEntity(dto);
        E saved = repository.save(entity);
        return entityToDTO(saved);
    }

    // Metodo Abstracto para actualizar
    public D update(Long id, D dto, JpaRepository<E, Long> repository) {
        E existingEntity = repository.findById(id).orElse(null);
        if (existingEntity == null)
            return null;

        E updatedEntity = updateEntity(existingEntity, dto);
        repository.save(updatedEntity);
        return entityToDTO(updatedEntity);
    }

    // Metodo Abstracto para Eliminar
    public boolean delete(Long id, JpaRepository<E, Long> repository) {
        if (!repository.existsById(id))
            return false;
        repository.deleteById(id);
        return true;
    }

    // Metodo Abstracto para Listar todos
    public List<D> getAll(JpaRepository<E, Long> repository) {
        return repository.findAll().stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    // Metodo Abstracto para Obtener por ID
    public D getById(Long id, JpaRepository<E, Long> repository) {
        return repository.findById(id)
                .map(this::entityToDTO)
                .orElse(null);
    }


}
