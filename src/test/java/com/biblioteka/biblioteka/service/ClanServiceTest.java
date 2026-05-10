package com.biblioteka.biblioteka.service;

import com.biblioteka.biblioteka.model.Clan;
import com.biblioteka.biblioteka.repository.ClanRepository;
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
class ClanServiceTest {

    @Mock
    private ClanRepository clanRepository;

    @InjectMocks
    private ClanService clanService;

    private Clan clan1;
    private Clan clan2;

    @BeforeEach
    void setUp() {
        clan1 = new Clan("Marko", "Markovic", "marko@email.com", "CLN-001");
        clan1.setId(1L);

        clan2 = new Clan("Ana", "Anic", "ana@email.com", "CLN-002");
        clan2.setId(2L);
    }

    @Test
    void getAll_trebaDaVratiSveClanove() {
        // Arrange
        when(clanRepository.findAll()).thenReturn(Arrays.asList(clan1, clan2));

        // Act
        List<Clan> rezultat = clanService.getAll();

        // Assert
        assertEquals(2, rezultat.size());
        verify(clanRepository, times(1)).findAll();
    }

    @Test
    void getById_trebaDaVratiClanaAkoPostoji() {
        // Arrange
        when(clanRepository.findById(1L)).thenReturn(Optional.of(clan1));

        // Act
        Optional<Clan> rezultat = clanService.getById(1L);

        // Assert
        assertTrue(rezultat.isPresent());
        assertEquals("Marko", rezultat.get().getIme());
        assertEquals("CLN-001", rezultat.get().getBrojClanskeKarte());
    }

    @Test
    void getById_trebaDaVratiPraznoAkoNemaClana() {
        // Arrange
        when(clanRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        Optional<Clan> rezultat = clanService.getById(99L);

        // Assert
        assertFalse(rezultat.isPresent());
    }

    @Test
    void save_trebaDaSacuvaClana() {
        // Arrange
        when(clanRepository.save(clan1)).thenReturn(clan1);

        // Act
        Clan sacuvan = clanService.save(clan1);

        // Assert
        assertNotNull(sacuvan);
        assertEquals("Marko", sacuvan.getIme());
        assertEquals("marko@email.com", sacuvan.getEmail());
        verify(clanRepository, times(1)).save(clan1);
    }

    @Test
    void delete_trebaDaPozoveRepository() {
        // Arrange
        doNothing().when(clanRepository).deleteById(1L);

        // Act
        clanService.delete(1L);

        // Assert
        verify(clanRepository, times(1)).deleteById(1L);
    }
}
