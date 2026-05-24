package com.biblioteka.biblioteka.controller;

import com.biblioteka.biblioteka.model.Autor;
import com.biblioteka.biblioteka.service.AutorService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
 
@WebMvcTest(AutorController.class)
class AutorControllerTest {
 
    @Autowired
    private MockMvc mockMvc;
 
    @MockBean
    private AutorService autorService;
 
    @Autowired
    private ObjectMapper objectMapper;
 
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
    void getAll_trebaDaVratiListuAutora() throws Exception {
        when(autorService.getAll()).thenReturn(Arrays.asList(autor1, autor2));
 
        mockMvc.perform(get("/autori"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].ime").value("Ivo"))
                .andExpect(jsonPath("$[1].ime").value("Mesa"));
    }
 
    @Test
    void getById_trebaDaVratiAutoraAkoPostoji() throws Exception {
        when(autorService.getById(1L)).thenReturn(Optional.of(autor1));
 
        mockMvc.perform(get("/autori/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ime").value("Ivo"))
                .andExpect(jsonPath("$.prezime").value("Andric"))
                .andExpect(jsonPath("$.nacionalnost").value("srpski"));
    }
 
    @Test
    void getById_trebaDaVrati404AkoNemaAutora() throws Exception {
        when(autorService.getById(99L)).thenReturn(Optional.empty());
 
        mockMvc.perform(get("/autori/99"))
                .andExpect(status().isNotFound());
    }
 
    @Test
    void save_trebaDaSacuvaIVratiAutora() throws Exception {
        when(autorService.save(any(Autor.class))).thenReturn(autor1);
 
        mockMvc.perform(post("/autori")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(autor1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ime").value("Ivo"))
                .andExpect(jsonPath("$.prezime").value("Andric"));
    }
 
    @Test
    void delete_trebaDaObriseAutora() throws Exception {
        doNothing().when(autorService).delete(1L);
 
        mockMvc.perform(delete("/autori/1"))
                .andExpect(status().isOk());
 
        verify(autorService, times(1)).delete(1L);
    }
}
