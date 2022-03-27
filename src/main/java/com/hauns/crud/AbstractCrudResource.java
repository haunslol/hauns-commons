package com.hauns.crud.api;

import com.hauns.quickjobsbackend.datahandling.service.AbstractCrudService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public abstract class AbstractCrudResource<I> {

    protected abstract AbstractCrudService<I> getService();

    @GetMapping("/{id}")
    public ResponseEntity<I> getUser(@PathVariable Long id) {

        return ResponseEntity.ok().body(getService().getObjectById(id));
    }

    @GetMapping("/list")
    public ResponseEntity<List<I>> getUsers(PageRequest pageRequest) {

        return ResponseEntity.ok().body(getService().list(pageRequest).toList());
    }
}
