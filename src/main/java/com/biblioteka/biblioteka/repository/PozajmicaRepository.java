package com.biblioteka.biblioteka.repository;

import com.biblioteka.biblioteka.model.Pozajmica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PozajmicaRepository extends JpaRepository<Pozajmica, Long> {
}
