package com.biblioteka.biblioteka.service;

import com.biblioteka.biblioteka.model.Pozajmica;
import com.biblioteka.biblioteka.repository.PozajmicaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PozajmicaService {

    private final PozajmicaRepository pozajmicaRepository;

    public PozajmicaService(PozajmicaRepository pozajmicaRepository) {
        this.pozajmicaRepository = pozajmicaRepository;
    }

    public List<Pozajmica> getAll() {
        return pozajmicaRepository.findAll();
    }

    public Optional<Pozajmica> getById(Long id) {
        return pozajmicaRepository.findById(id);
    }

    public Pozajmica save(Pozajmica pozajmica) {
        return pozajmicaRepository.save(pozajmica);
    }

    public void delete(Long id) {
        pozajmicaRepository.deleteById(id);
    }
}
