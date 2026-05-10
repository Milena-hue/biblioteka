package com.biblioteka.biblioteka.controller;

import com.biblioteka.biblioteka.model.Clan;
import com.biblioteka.biblioteka.service.ClanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clanovi")
public class ClanController {

    private final ClanService clanService;

    public ClanController(ClanService clanService) {
        this.clanService = clanService;
    }

    @GetMapping
    public List<Clan> getAll() {
        return clanService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Clan> getById(@PathVariable Long id) {
        return clanService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Clan save(@RequestBody Clan clan) {
        return clanService.save(clan);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clanService.delete(id);
        return ResponseEntity.ok().build();
    }
}
