package com.biblioteka.biblioteka.service;

import com.biblioteka.biblioteka.model.Knjiga;
import com.biblioteka.biblioteka.repository.KnjigaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KnjigaService {

    private final KnjigaRepository knjigaRepository;

    public KnjigaService(KnjigaRepository knjigaRepository) {
        this.knjigaRepository = knjigaRepository;
    }

    public List<Knjiga> getAll() {
        return knjigaRepository.findAll();
    }

    public Optional<Knjiga> getById(Long id) {
        return knjigaRepository.findById(id);
    }

    public Knjiga save(Knjiga knjiga) {
        return knjigaRepository.save(knjiga);
    }

    public void delete(Long id) {
        knjigaRepository.deleteById(id);
    }

    public List<Knjiga> getDostupne() {
        return knjigaRepository.findByDostupna(true);
    }

    public Optional<Knjiga> promeniDostupnost(Long id, Boolean dostupna) {
        Optional<Knjiga> knjiga = knjigaRepository.findById(id);
        if (knjiga.isPresent()) {
            knjiga.get().setDostupna(dostupna);
            knjigaRepository.save(knjiga.get());
        }
        return knjiga;
    }
}
