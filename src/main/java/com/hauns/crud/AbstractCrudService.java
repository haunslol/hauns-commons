package com.hauns.crud;

import com.hauns.common.interfaces.IIdBaseEntity;
import com.hauns.errorhandling.RestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

public abstract class AbstractCrudService<I extends IIdBaseEntity<N>, N extends Number> {

  protected abstract JpaRepository<I, N> getRepository();

  public I patch(I object) {
    existenceCheck(object.getId());
    preSave(object, CrudMethod.PATCH);
    return getRepository().save(object);
  }

  public I save(I object) {
    I result;
    if (object.getId() != null) {
      result = update(object);
    } else {
      result = create(object);
    }
    getRepository().flush();
    return result;
  }

  protected I update(I object) {
    existenceCheck(object.getId());
    preSave(object, CrudMethod.UPDATE);
    return getRepository().save(object);
  }

  protected void preSave(I object, CrudMethod crudMethod) {}

  protected I create(I object) throws EntityExistsException {
    if (object.getId() != null) {
      throw new EntityExistsException();
    }
    preSave(object, CrudMethod.CREATE);
    return getRepository().save(object);
  }

  public void deleteById(N id) {
    existenceCheck(id);
    getRepository().deleteById(id);
  }

  protected void existenceCheck(N id) throws RestException {
    if (id == null || !getRepository().existsById(id)) {
      throw new RestException(HttpStatus.NOT_FOUND, "global.entrynotfound");
    }
  }

  public I getById(N id) throws EntityNotFoundException {
    existenceCheck(id);
    return getRepository().getById(id);
  }

  public Page<I> list(PageRequest pageRequest) {
    return getRepository().findAll(pageRequest);
  }
}
