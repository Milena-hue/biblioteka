package com.biblioteka.biblioteka.controller;

import com.biblioteka.biblioteka.model.Autor;
import com.biblioteka.biblioteka.model.Knjiga;
import com.biblioteka.biblioteka.service.KnjigaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
 
import java.util.Arrays;
import java.util.Optional;
 
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
 
@WebMvcTest(KnjigaController.class)
class KnjigaControllerTest {
 
    @Autowired
    private MockMvc mockMvc;
 
    @MockBean
    private KnjigaService knjigaService;
 
    @Autowired
    private ObjectMapper objectMapper;
 
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
    void getAll_trebaDaVratiSveKnjige() throws Exception {
        when(knjigaService.getAll()).thenReturn(Arrays.asList(knjiga1, knjiga2));
 
        mockMvc.perform(get("/knjige"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].naslov").value("Na Drini cuprija"))
                .andExpect(jsonPath("$[1].naslov").value("Travnicka hronika"));
    }
 
    @Test
    void getById_trebaDaVratiKnjiguAkoPostoji() throws Exception {
        when(knjigaService.getById(1L)).thenReturn(Optional.of(knjiga1));
 
        mockMvc.perform(get("/knjige/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.naslov").value("Na Drini cuprija"))
                .andExpect(jsonPath("$.isbn").value("978-86-01-00001-1"))
                .andExpect(jsonPath("$.dostupna").value(true));
    }
 
    @Test
    void getById_trebaDaVrati404AkoNemaKnjige() throws Exception {
        when(knjigaService.getById(99L)).thenReturn(Optional.empty());
 
        mockMvc.perform(get("/knjige/99"))
                .andExpect(status().isNotFound());
    }
 
    @Test
    void getDostupne_trebaDaVratiSamoDostupneKnjige() throws Exception {
        when(knjigaService.getDostupne()).thenReturn(Arrays.asList(knjiga1));
 
        mockMvc.perform(get("/knjige/dostupne"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].dostupna").value(true));
    }
 
    @Test
    void save_trebaDaSacuvaKnjigu() throws Exception {
        when(knjigaService.save(any(Knjiga.class))).thenReturn(knjiga1);
 
        mockMvc.perform(post("/knjige")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(knjiga1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.naslov").value("Na Drini cuprija"));
    }
 
    @Test
    void promeniDostupnost_trebaDaAzuriraStatus() throws Exception {
        knjiga1.setDostupna(false);
        when(knjigaService.promeniDostupnost(eq(1L), eq(false))).thenReturn(Optional.of(knjiga1));
 
        mockMvc.perform(put("/knjige/1/dostupnost")
                        .param("dostupna", "false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dostupna").value(false));
    }
 
    @Test
    void promeniDostupnost_trebaDaVrati404AkoKnjigaNijeNadjena() throws Exception {
        when(knjigaService.promeniDostupnost(eq(99L), any())).thenReturn(Optional.empty());
 
        mockMvc.perform(put("/knjige/99/dostupnost")
                        .param("dostupna", "true"))
                .andExpect(status().isNotFound());
    }
 
    @Test
    void delete_trebaDaObriseKnjigu() throws Exception {
        doNothing().when(knjigaService).delete(1L);
 
        mockMvc.perform(delete("/knjige/1"))
                .andExpect(status().isOk());
 
        verify(knjigaService, times(1)).delete(1L);
    }
}
