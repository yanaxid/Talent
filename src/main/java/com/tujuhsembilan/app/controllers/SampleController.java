package com.tujuhsembilan.app.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tujuhsembilan.app.models.SampleModel;
import com.tujuhsembilan.app.repository.SampleRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/sample")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SampleController {

  private final SampleRepository repo;

  @GetMapping
  public ResponseEntity<?> get(Pageable pagination) {
    var result = repo.findAll(pagination);
    var model = PagedModel.of(
        result.getContent(),
        new PagedModel.PageMetadata(
            result.getSize(),
            result.getNumber(),
            result.getTotalElements(),
            result.getTotalPages()));
    return ResponseEntity.ok(model);
  }

  @PostMapping
  public ResponseEntity<?> post(@RequestBody SampleModel body) {
    return ResponseEntity.status(HttpStatus.CREATED).body(RepresentationModel.of(repo.save(body)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> put(@PathVariable("id") String id, @RequestBody SampleModel body) {
    var entity = repo.findById(UUID.fromString(id));
    if (entity.isEmpty()) {
      throw new EntityNotFoundException();
    }

    body.setId(entity.get().getId());
    return ResponseEntity.status(HttpStatus.CREATED).body(RepresentationModel.of(repo.save(body)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable("id") String id) {
    var entity = repo.findById(UUID.fromString(id));
    if (entity.isEmpty()) {
      throw new EntityNotFoundException();
    }

    repo.delete(entity.get());
    return ResponseEntity.ok().build();
  }

}
