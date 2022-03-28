package com.hauns.crud;

import com.hauns.common.interfaces.IIdBaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

public abstract class AbstractCrudService<I extends IIdBaseEntity<ID>, ID extends Number> {

    protected abstract JpaRepository<I, ID> getRepository();

    public I patch(I object) {
        existenceCheck(object.getId());
        preSave(object, CrudMethod.PATCH);
        return getRepository().save(object);
    }


    public I update(I object) {
        existenceCheck(object.getId());
        preSave(object, CrudMethod.UPDATE);
        return getRepository().save(object);
    }

    protected void preSave(I object, CrudMethod crudMethod) {

    }


    public I create(I object) throws EntityExistsException {
        if (object.getId() != null) {
            throw new EntityExistsException();
        }
        preSave(object, CrudMethod.CREATE);
        return getRepository().save(object);
    }


    public void deleteById(ID id) {
        existenceCheck(id);
        getRepository().deleteById(id);
    }


    protected void existenceCheck(ID id) throws EntityNotFoundException {
        if (id == null || getRepository().findById(id).isEmpty()) {
//            throw new EntityNotFoundException();
        }
    }


    public I getById(ID id) throws EntityNotFoundException {
        existenceCheck(id);
        return getRepository().getById(id);
    }


    public Page<I> list(PageRequest pageRequest) {
        return getRepository().findAll(pageRequest);
    }
}
