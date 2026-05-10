package com.biblioteka.biblioteka.service;

import com.biblioteka.biblioteka.model.Autor;
import com.biblioteka.biblioteka.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public List<Autor> getAll() {
        return autorRepository.findAll();
    }

    public Optional<Autor> getById(Long id) {
        return autorRepository.findById(id);
    }

    public Autor save(Autor autor) {
        return autorRepository.save(autor);
    }

    public void delete(Long id) {
        autorRepository.deleteById(id);
    }
}
