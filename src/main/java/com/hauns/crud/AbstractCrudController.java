package com.hauns.crud;

import com.hauns.common.interfaces.IIdBaseEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class AbstractCrudController<I extends IIdBaseEntity<ID>, ID extends Number> {

    protected abstract AbstractCrudService<I, ID> getService();

    @GetMapping("/{id}")
    public ResponseEntity<I> getObjectById(@PathVariable(name = "id") ID id) {
        return ResponseEntity.ok().body(getService().getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<I> update(@PathVariable ID id, @RequestBody I object) {

        return ResponseEntity.ok().body(getService().update(object));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<I> patch(@PathVariable ID id, @RequestBody I object) {

        return ResponseEntity.ok().body(getService().patch(object));
    }

    @PostMapping
    public ResponseEntity<I> create(@RequestBody I object) {

        return ResponseEntity.ok().body(getService().create(object));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable(name = "id") ID id) {
        getService().deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<I>> list(PageRequest pageRequest) {

        return ResponseEntity.ok().body(getService().list(pageRequest).toList());
    }
}
