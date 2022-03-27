package com.hauns.crud;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface AbstractCrudService<I> {

    I save(I object);

    I getObjectById(Long id);

    void deleteById(Long id);

    Page<I> list(PageRequest pageRequest);
}
