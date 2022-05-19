package com.hauns.crud;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hauns.common.interfaces.IIdBaseEntity;
import com.hauns.errorhandling.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.json.JsonPatch;
import javax.json.JsonStructure;
import javax.json.JsonValue;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

public abstract class AbstractCrudController<I extends IIdBaseEntity<N>, R, N extends Number> {

  private ObjectMapper objectMapper;
  private Class<I> beanClass;
  private Class<R> resultDtoClass;

  @Autowired
  public final void setObjectMapper(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @SuppressWarnings("unchecked")
  private Class<I> getBeanClass() {
    if (beanClass == null) {
      this.beanClass =
          (Class<I>)
              ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    return beanClass;
  }

  @SuppressWarnings("unchecked")
  private Class<R> getResultDtoClass() {
    if (resultDtoClass == null) {
      this.resultDtoClass =
          (Class<R>)
              ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    return resultDtoClass;
  }

  protected abstract AbstractCrudService<I, N> getService();

  @GetMapping("/{id}")
  public ResponseEntity<R> getObjectById(@PathVariable(name = "id") N id) {
    return ResponseEntity.ok().body(mappToDto(getService().getById(id)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<R> update(@PathVariable N id, @RequestBody I object) {

    return ResponseEntity.ok().body(mappToDto(getService().update(object)));
  }

  protected List<R> mappToDtos(Collection<I> beans) {
    return beans.stream().map(i -> objectMapper.convertValue(beans, resultDtoClass)).toList();
  }

  protected R mappToDto(I bean) {
    return objectMapper.convertValue(bean, resultDtoClass);
  }

  @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
  public ResponseEntity<R> path(@PathVariable N id, @RequestBody JsonPatch patch) {
    try {
      I targetBean = getService().getById(id);
      targetBean = patch(patch, targetBean, getBeanClass());
      getService().update(targetBean);
      return ResponseEntity.ok(mappToDto(targetBean));
    } catch (RestException e) {
      throw e;
    } catch (Exception e) {
      throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "", e);
    }
  }

  public <T> T patch(JsonPatch patch, T targetBean, Class<T> beanClass) {

    // Convert the Java bean to a JSON document
    JsonStructure target = objectMapper.convertValue(targetBean, JsonStructure.class);

    // Apply the JSON Patch to the JSON document
    JsonValue patched = patch.apply(target);

    // Convert the JSON document to a Java bean and return it
    return objectMapper.convertValue(patched, beanClass);
  }

  @PostMapping
  public ResponseEntity<R> create(@RequestBody I object) {
    return ResponseEntity.ok().body(mappToDto(getService().create(object)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<R> delete(@PathVariable(name = "id") N id) {
    getService().deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  public ResponseEntity<List<R>> list(PageRequest pageRequest) {

    return ResponseEntity.ok().body(mappToDtos(getService().list(pageRequest).toList()));
  }
}
