package com.biblioteka.biblioteka.controller;

import com.biblioteka.biblioteka.model.Clan;
import com.biblioteka.biblioteka.service.ClanService;
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
 
@WebMvcTest(ClanController.class)
class ClanControllerTest {
 
    @Autowired
    private MockMvc mockMvc;
 
    @MockBean
    private ClanService clanService;
 
    @Autowired
    private ObjectMapper objectMapper;
 
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
    void getAll_trebaDaVratiSveClanove() throws Exception {
        when(clanService.getAll()).thenReturn(Arrays.asList(clan1, clan2));
 
        mockMvc.perform(get("/clanovi"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].ime").value("Marko"))
                .andExpect(jsonPath("$[1].ime").value("Ana"));
    }
 
    @Test
    void getById_trebaDaVratiClanaAkoPostoji() throws Exception {
        when(clanService.getById(1L)).thenReturn(Optional.of(clan1));
 
        mockMvc.perform(get("/clanovi/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ime").value("Marko"))
                .andExpect(jsonPath("$.email").value("marko@email.com"))
                .andExpect(jsonPath("$.brojClanskeKarte").value("CLN-001"));
    }
 
    @Test
    void getById_trebaDaVrati404AkoNemaClana() throws Exception {
        when(clanService.getById(99L)).thenReturn(Optional.empty());
 
        mockMvc.perform(get("/clanovi/99"))
                .andExpect(status().isNotFound());
    }
 
    @Test
    void save_trebaDaSacuvaClana() throws Exception {
        when(clanService.save(any(Clan.class))).thenReturn(clan1);
 
        mockMvc.perform(post("/clanovi")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clan1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ime").value("Marko"))
                .andExpect(jsonPath("$.email").value("marko@email.com"));
    }
 
    @Test
    void delete_trebaDaObriseClan() throws Exception {
        doNothing().when(clanService).delete(1L);
 
        mockMvc.perform(delete("/clanovi/1"))
                .andExpect(status().isOk());
 
        verify(clanService, times(1)).delete(1L);
    }
}
