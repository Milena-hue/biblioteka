package com.biblioteka.biblioteka.service;


import com.biblioteka.biblioteka.model.Autor;
import com.biblioteka.biblioteka.model.Clan;
import com.biblioteka.biblioteka.model.Knjiga;
import com.biblioteka.biblioteka.model.Pozajmica;
import com.biblioteka.biblioteka.repository.PozajmicaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
 
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
 
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
 
@ExtendWith(MockitoExtension.class)
class PozajmicaServiceTest {
 
    @Mock
    private PozajmicaRepository pozajmicaRepository;
 
    @InjectMocks
    private PozajmicaService pozajmicaService;
 
    private Pozajmica pozajmica1;
    private Pozajmica pozajmica2;
    private Knjiga knjiga;
    private Clan clan;
 
    @BeforeEach
    void setUp() {
        Autor autor = new Autor("Ivo", "Andric", "srpski");
        autor.setId(1L);
 
        knjiga = new Knjiga("Na Drini cuprija", "978-86-01-00001-1", 1945, false, autor);
        knjiga.setId(1L);
 
        clan = new Clan("Marko", "Markovic", "marko@email.com", "CLN-001");
        clan.setId(1L);
 
        pozajmica1 = new Pozajmica(
                LocalDate.of(2024, 1, 10),
                LocalDate.of(2024, 1, 24),
                false,
                knjiga,
                clan
        );
        pozajmica1.setId(1L);
 
        pozajmica2 = new Pozajmica(
                LocalDate.of(2024, 2, 5),
                LocalDate.of(2024, 2, 19),
                true,
                knjiga,
                clan
        );
        pozajmica2.setId(2L);
    }
 
    @Test
    void getAll_trebaDaVratiSvePozajmice() {
        // Arrange
        when(pozajmicaRepository.findAll()).thenReturn(Arrays.asList(pozajmica1, pozajmica2));
 
        // Act
        List<Pozajmica> rezultat = pozajmicaService.getAll();
 
        // Assert
        assertEquals(2, rezultat.size());
        verify(pozajmicaRepository, times(1)).findAll();
    }
 
    @Test
    void getAll_trebaDaVratiPrazanuListuAkoNemaPozajmica() {
        // Arrange
        when(pozajmicaRepository.findAll()).thenReturn(Arrays.asList());
 
        // Act
        List<Pozajmica> rezultat = pozajmicaService.getAll();
 
        // Assert
        assertTrue(rezultat.isEmpty());
    }
 
    @Test
    void getById_trebaDaVratiPozajmicuAkoPostoji() {
        // Arrange
        when(pozajmicaRepository.findById(1L)).thenReturn(Optional.of(pozajmica1));
 
        // Act
        Optional<Pozajmica> rezultat = pozajmicaService.getById(1L);
 
        // Assert
        assertTrue(rezultat.isPresent());
        assertEquals(LocalDate.of(2024, 1, 10), rezultat.get().getDatumPozajmice());
        assertFalse(rezultat.get().getVracena());
    }
 
    @Test
    void getById_trebaDaVratiPraznoAkoNemaPozajmice() {
        // Arrange
        when(pozajmicaRepository.findById(99L)).thenReturn(Optional.empty());
 
        // Act
        Optional<Pozajmica> rezultat = pozajmicaService.getById(99L);
 
        // Assert
        assertFalse(rezultat.isPresent());
    }
 
    @Test
    void save_trebaDaSacuvaPozajmicu() {
        // Arrange
        when(pozajmicaRepository.save(pozajmica1)).thenReturn(pozajmica1);
 
        // Act
        Pozajmica sacuvana = pozajmicaService.save(pozajmica1);
 
        // Assert
        assertNotNull(sacuvana);
        assertEquals(knjiga, sacuvana.getKnjiga());
        assertEquals(clan, sacuvana.getClan());
        assertFalse(sacuvana.getVracena());
        verify(pozajmicaRepository, times(1)).save(pozajmica1);
    }
 
    @Test
    void save_trebaDaSacuvaPozajmicuSaVracenimStatusom() {
        // Arrange
        when(pozajmicaRepository.save(pozajmica2)).thenReturn(pozajmica2);
 
        // Act
        Pozajmica sacuvana = pozajmicaService.save(pozajmica2);
 
        // Assert
        assertTrue(sacuvana.getVracena());
        assertEquals(LocalDate.of(2024, 2, 5), sacuvana.getDatumPozajmice());
    }
 
    @Test
    void delete_trebaDaPozoveRepository() {
        // Arrange
        doNothing().when(pozajmicaRepository).deleteById(1L);
 
        // Act
        pozajmicaService.delete(1L);
 
        // Assert
        verify(pozajmicaRepository, times(1)).deleteById(1L);
    }
}
