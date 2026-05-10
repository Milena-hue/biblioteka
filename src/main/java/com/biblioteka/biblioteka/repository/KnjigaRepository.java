package com.biblioteka.biblioteka.repository;

import com.biblioteka.biblioteka.model.Knjiga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KnjigaRepository extends JpaRepository<Knjiga, Long> {
    List<Knjiga> findByDostupna(Boolean dostupna);
}
