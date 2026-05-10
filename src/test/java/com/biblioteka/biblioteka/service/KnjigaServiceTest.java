package com.biblioteka.biblioteka.service;

import com.biblioteka.biblioteka.model.Autor;
import com.biblioteka.biblioteka.model.Knjiga;
import com.biblioteka.biblioteka.repository.KnjigaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KnjigaServiceTest {

    @Mock
    private KnjigaRepository knjigaRepository;

    @InjectMocks
    private KnjigaService knjigaService;

    private Autor autor;
    private Knjiga knjiga1;
    private Knjiga knjiga2;

    @BeforeEach
    void setUp() {
        autor = new Autor("Ivo", "Andric", "srpski");
        autor.setId(1L);

        knjiga1 = new Knjiga("Na Drini cuprija", "978-86-01-00001-1", 1945, true, autor);
        knjiga1.setId(1L);

        knjiga2 = new Knjiga("Travnicka hronika", "978-86-01-00002-2", 1945, false, autor);
        knjiga2.setId(2L);
    }

    @Test
    void getAll_trebaDaVratiSveKnjige() {
        // Arrange
        when(knjigaRepository.findAll()).thenReturn(Arrays.asList(knjiga1, knjiga2));

        // Act
        List<Knjiga> rezultat = knjigaService.getAll();

        // Assert
        assertEquals(2, rezultat.size());
        verify(knjigaRepository, times(1)).findAll();
    }

    @Test
    void getById_trebaDaVratiKnjiguAkoPostoji() {
        // Arrange
        when(knjigaRepository.findById(1L)).thenReturn(Optional.of(knjiga1));

        // Act
        Optional<Knjiga> rezultat = knjigaService.getById(1L);

        // Assert
        assertTrue(rezultat.isPresent());
        assertEquals("Na Drini cuprija", rezultat.get().getNaslov());
    }

    @Test
    void getById_trebaDaVratiPraznoAkoNemaKnjige() {
        // Arrange
        when(knjigaRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        Optional<Knjiga> rezultat = knjigaService.getById(99L);

        // Assert
        assertFalse(rezultat.isPresent());
    }

    @Test
    void save_trebaDaSacuvaKnjigu() {
        // Arrange
        when(knjigaRepository.save(knjiga1)).thenReturn(knjiga1);

        // Act
        Knjiga sacuvana = knjigaService.save(knjiga1);

        // Assert
        assertNotNull(sacuvana);
        assertEquals("Na Drini cuprija", sacuvana.getNaslov());
        verify(knjigaRepository, times(1)).save(knjiga1);
    }

    @Test
    void getDostupne_trebaDaVratiSamoDostupneKnjige() {
        // Arrange
        when(knjigaRepository.findByDostupna(true)).thenReturn(Arrays.asList(knjiga1));

        // Act
        List<Knjiga> dostupne = knjigaService.getDostupne();

        // Assert
        assertEquals(1, dostupne.size());
        assertTrue(dostupne.get(0).getDostupna());
    }

    @Test
    void delete_trebaDaPozoveRepository() {
        // Arrange
        doNothing().when(knjigaRepository).deleteById(1L);

        // Act
        knjigaService.delete(1L);

        // Assert
        verify(knjigaRepository, times(1)).deleteById(1L);
    }

    @Test
    void promeniDostupnost_trebaDaPromeniStatus() {
        // Arrange
        when(knjigaRepository.findById(1L)).thenReturn(Optional.of(knjiga1));
        when(knjigaRepository.save(any(Knjiga.class))).thenReturn(knjiga1);

        // Act
        Optional<Knjiga> rezultat = knjigaService.promeniDostupnost(1L, false);

        // Assert
        assertTrue(rezultat.isPresent());
        verify(knjigaRepository, times(1)).save(any(Knjiga.class));
    }
}
