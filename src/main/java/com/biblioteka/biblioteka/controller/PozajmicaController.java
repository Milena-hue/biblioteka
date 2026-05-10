package com.biblioteka.biblioteka.controller;

import com.biblioteka.biblioteka.model.Pozajmica;
import com.biblioteka.biblioteka.service.PozajmicaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pozajmice")
public class PozajmicaController {

    private final PozajmicaService pozajmicaService;

    public PozajmicaController(PozajmicaService pozajmicaService) {
        this.pozajmicaService = pozajmicaService;
    }

    @GetMapping
    public List<Pozajmica> getAll() {
        return pozajmicaService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pozajmica> getById(@PathVariable Long id) {
        return pozajmicaService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Pozajmica save(@RequestBody Pozajmica pozajmica) {
        return pozajmicaService.save(pozajmica);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pozajmicaService.delete(id);
        return ResponseEntity.ok().build();
    }
}
