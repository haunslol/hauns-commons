package com.hauns.crud;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class AbstractCrudServiceImpl<I> implements AbstractCrudService<I> {

    protected abstract JpaRepository<I, Long> getRepository();

    @Override
    public I save(I object) {
        return getRepository().save(object);
    }

    @Override
    public void deleteById(Long id) {
        getRepository().deleteById(id);
    }

    @Override
    public I getObjectById(Long id) {
        return getRepository().getById(id);
    }

    @Override
    public Page<I> list(PageRequest pageRequest) {
        return getRepository().findAll(pageRequest);
    }
}
