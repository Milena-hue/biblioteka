package com.biblioteka.biblioteka.service;

import com.biblioteka.biblioteka.model.Autor;
import com.biblioteka.biblioteka.repository.AutorRepository;
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
class AutorServiceTest {
 
    @Mock
    private AutorRepository autorRepository;
 
    @InjectMocks
    private AutorService autorService;
 
    private Autor autor1;
    private Autor autor2;
 
    @BeforeEach
    void setUp() {
        autor1 = new Autor("Ivo", "Andric", "srpski");
        autor1.setId(1L);
 
        autor2 = new Autor("Mesa", "Selimovic", "srpski");
        autor2.setId(2L);
    }
 
    @Test
    void getAll_trebaDaVratiSveAutore() {
        // Arrange
        when(autorRepository.findAll()).thenReturn(Arrays.asList(autor1, autor2));
 
        // Act
        List<Autor> rezultat = autorService.getAll();
 
        // Assert
        assertEquals(2, rezultat.size());
        verify(autorRepository, times(1)).findAll();
    }
 
    @Test
    void getAll_trebaDaVratiPrazanuListuAkoNemaAutora() {
        // Arrange
        when(autorRepository.findAll()).thenReturn(Arrays.asList());
 
        // Act
        List<Autor> rezultat = autorService.getAll();
 
        // Assert
        assertTrue(rezultat.isEmpty());
        verify(autorRepository, times(1)).findAll();
    }
 
    @Test
    void getById_trebaDaVratiAutoraAkoPostoji() {
        // Arrange
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor1));
 
        // Act
        Optional<Autor> rezultat = autorService.getById(1L);
 
        // Assert
        assertTrue(rezultat.isPresent());
        assertEquals("Ivo", rezultat.get().getIme());
        assertEquals("Andric", rezultat.get().getPrezime());
        assertEquals("srpski", rezultat.get().getNacionalnost());
    }
 
    @Test
    void getById_trebaDaVratiPraznoAkoNemaAutora() {
        // Arrange
        when(autorRepository.findById(99L)).thenReturn(Optional.empty());
 
        // Act
        Optional<Autor> rezultat = autorService.getById(99L);
 
        // Assert
        assertFalse(rezultat.isPresent());
    }
 
    @Test
    void save_trebaDaSacuvaAutora() {
        // Arrange
        when(autorRepository.save(autor1)).thenReturn(autor1);
 
        // Act
        Autor sacuvan = autorService.save(autor1);
 
        // Assert
        assertNotNull(sacuvan);
        assertEquals("Ivo", sacuvan.getIme());
        assertEquals("Andric", sacuvan.getPrezime());
        verify(autorRepository, times(1)).save(autor1);
    }
 
    @Test
    void save_trebaDaVratiSacuvanogAutoraSSvimPodacima() {
        // Arrange
        Autor noviAutor = new Autor("Dobrica", "Cosic", "srpski");
        when(autorRepository.save(noviAutor)).thenReturn(noviAutor);
 
        // Act
        Autor rezultat = autorService.save(noviAutor);
 
        // Assert
        assertEquals("Dobrica", rezultat.getIme());
        assertEquals("Cosic", rezultat.getPrezime());
        assertEquals("srpski", rezultat.getNacionalnost());
    }
 
    @Test
    void delete_trebaDaPozoveRepository() {
        // Arrange
        doNothing().when(autorRepository).deleteById(1L);
 
        // Act
        autorService.delete(1L);
 
        // Assert
        verify(autorRepository, times(1)).deleteById(1L);
    }
}
