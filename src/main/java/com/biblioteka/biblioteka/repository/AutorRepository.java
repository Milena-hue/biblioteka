package com.biblioteka.biblioteka.repository;
import com.biblioteka.biblioteka.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
}
