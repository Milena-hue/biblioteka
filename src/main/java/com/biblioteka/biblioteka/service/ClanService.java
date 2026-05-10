package com.biblioteka.biblioteka.service;

import com.biblioteka.biblioteka.model.Clan;
import com.biblioteka.biblioteka.repository.ClanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClanService {

    private final ClanRepository clanRepository;

    public ClanService(ClanRepository clanRepository) {
        this.clanRepository = clanRepository;
    }

    public List<Clan> getAll() {
        return clanRepository.findAll();
    }

    public Optional<Clan> getById(Long id) {
        return clanRepository.findById(id);
    }

    public Clan save(Clan clan) {
        return clanRepository.save(clan);
    }

    public void delete(Long id) {
        clanRepository.deleteById(id);
    }
}
