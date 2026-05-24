package com.biblioteka.biblioteka.controller;

import com.biblioteka.biblioteka.model.Autor;
import com.biblioteka.biblioteka.model.Clan;
import com.biblioteka.biblioteka.model.Knjiga;
import com.biblioteka.biblioteka.model.Pozajmica;
import com.biblioteka.biblioteka.service.PozajmicaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
 
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
 
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
 
@WebMvcTest(PozajmicaController.class)
class PozajmicaControllerTest {
 
    @Autowired
    private MockMvc mockMvc;
 
    @MockBean
    private PozajmicaService pozajmicaService;
 
    private ObjectMapper objectMapper;
 
    private Pozajmica pozajmica1;
    private Pozajmica pozajmica2;
 
    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
 
        Autor autor = new Autor("Ivo", "Andric", "srpski");
        autor.setId(1L);
 
        Knjiga knjiga = new Knjiga("Na Drini cuprija", "978-86-01-00001-1", 1945, false, autor);
        knjiga.setId(1L);
 
        Clan clan = new Clan("Marko", "Markovic", "marko@email.com", "CLN-001");
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
    void getAll_trebaDaVratiSvePozajmice() throws Exception {
        when(pozajmicaService.getAll()).thenReturn(Arrays.asList(pozajmica1, pozajmica2));
 
        mockMvc.perform(get("/pozajmice"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].vracena").value(false))
                .andExpect(jsonPath("$[1].vracena").value(true));
    }
 
    @Test
    void getById_trebaDaVratiPozajmicuAkoPostoji() throws Exception {
        when(pozajmicaService.getById(1L)).thenReturn(Optional.of(pozajmica1));
 
        mockMvc.perform(get("/pozajmice/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vracena").value(false))
                .andExpect(jsonPath("$.knjiga.naslov").value("Na Drini cuprija"))
                .andExpect(jsonPath("$.clan.ime").value("Marko"));
    }
 
    @Test
    void getById_trebaDaVrati404AkoNemaPozajmice() throws Exception {
        when(pozajmicaService.getById(99L)).thenReturn(Optional.empty());
 
        mockMvc.perform(get("/pozajmice/99"))
                .andExpect(status().isNotFound());
    }
 
    @Test
    void save_trebaDaSacuvaPozajmicu() throws Exception {
        when(pozajmicaService.save(any(Pozajmica.class))).thenReturn(pozajmica1);
 
        mockMvc.perform(post("/pozajmice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pozajmica1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vracena").value(false));
    }
 
    @Test
    void delete_trebaDaObrisePozajmicu() throws Exception {
        doNothing().when(pozajmicaService).delete(1L);
 
        mockMvc.perform(delete("/pozajmice/1"))
                .andExpect(status().isOk());
 
        verify(pozajmicaService, times(1)).delete(1L);
    }
}