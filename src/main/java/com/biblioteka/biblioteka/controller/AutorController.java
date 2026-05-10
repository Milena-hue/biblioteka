package com.biblioteka.biblioteka.controller;

import com.biblioteka.biblioteka.model.Autor;
import com.biblioteka.biblioteka.service.AutorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autori")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @GetMapping
    public List<Autor> getAll() {
        return autorService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Autor> getById(@PathVariable Long id) {
        return autorService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Autor save(@RequestBody Autor autor) {
        return autorService.save(autor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        autorService.delete(id);
        return ResponseEntity.ok().build();
    }
}
