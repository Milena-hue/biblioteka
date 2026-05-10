package com.biblioteka.biblioteka.controller;

import com.biblioteka.biblioteka.model.Knjiga;
import com.biblioteka.biblioteka.service.KnjigaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/knjige")
public class KnjigaController {

    private final KnjigaService knjigaService;

    public KnjigaController(KnjigaService knjigaService) {
        this.knjigaService = knjigaService;
    }

    @GetMapping
    public List<Knjiga> getAll() {
        return knjigaService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Knjiga> getById(@PathVariable Long id) {
        return knjigaService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/dostupne")
    public List<Knjiga> getDostupne() {
        return knjigaService.getDostupne();
    }

    @PostMapping
    public Knjiga save(@RequestBody Knjiga knjiga) {
        return knjigaService.save(knjiga);
    }

    @PutMapping("/{id}/dostupnost")
    public ResponseEntity<Knjiga> promeniDostupnost(@PathVariable Long id, @RequestParam Boolean dostupna) {
        return knjigaService.promeniDostupnost(id, dostupna)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        knjigaService.delete(id);
        return ResponseEntity.ok().build();
    }
}
