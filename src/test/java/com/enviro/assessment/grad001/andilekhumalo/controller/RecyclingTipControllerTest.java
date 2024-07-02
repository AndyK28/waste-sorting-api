package com.enviro.assessment.grad001.andilekhumalo.controller;

import com.enviro.assessment.grad001.andilekhumalo.exception.NotFoundException;
import com.enviro.assessment.grad001.andilekhumalo.model.RecyclingTips;
import com.enviro.assessment.grad001.andilekhumalo.service.RecyclingTipService;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RecyclingTipController.class)
public class RecyclingTipControllerTest {
    private String recyclingTip;
    private String updatedRecyclingTip;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecyclingTipService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recyclingTip = "Recycle plastic bottles in the recycling bin";
        updatedRecyclingTip = "Recycle cardboard boxes in the designated bin";
    }

    @AfterEach
    void tearDown() {
        recyclingTip = null;
        updatedRecyclingTip = null;
    }

    @Test
    public void testGetAllRecyclingTips_Success() throws Exception {
        // Arrange
        RecyclingTips recyclingTips = new RecyclingTips();
        recyclingTips.setId(1L);
        recyclingTips.setTip(recyclingTip);
        when(service.getAllRecyclingTips()).thenReturn(Collections.singletonList(recyclingTips));

        // Act & Assert
        mockMvc.perform(get("/api/recycling-tips")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(recyclingTips.getId()))
                .andExpect(jsonPath("$[0].tip").value(recyclingTips.getTip()));
    }

    @Test
    public void testGetAllRecyclingTips_EmptyList() throws Exception {
        // Arrange
        when(service.getAllRecyclingTips()).thenThrow(new NotFoundException("Error 404: Not Found"));

        // Act && Assert
        mockMvc.perform(get("/api/recycling-tips")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetRecyclingTipById_Success() throws Exception {
        // Arrange
        Long id = 1L;
        RecyclingTips recyclingTips = new RecyclingTips();
        recyclingTips.setId(id);
        recyclingTips.setTip(recyclingTip);
        when(service.getRecyclingTipById(id)).thenReturn(recyclingTips);

        // Act & Assert
        mockMvc.perform(get("/api/recycling-tips/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(recyclingTips.getId()))
                .andExpect(jsonPath("$.tip").value(recyclingTips.getTip()));
    }

    @Test
    public void testGetRecyclingTipById_NotFound() throws Exception {
        // Arrange
        Long id = 1L;
        when(service.getRecyclingTipById(id)).thenThrow(new NotFoundException("Error 404: Not Found"));

        // Act && Assert
        mockMvc.perform(get("/api/recycling-tips/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddRecyclingTips_Success() throws Exception {
        // Arrange
        Long id = 1L;
        RecyclingTips newRecyclingTip = new RecyclingTips();
        newRecyclingTip.setId(id);
        newRecyclingTip.setTip(recyclingTip);
        when(service.addRecyclingTip(any(RecyclingTips.class))).thenReturn(newRecyclingTip);

        // Act & Assert
        mockMvc.perform(post("/api/recycling-tips")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"tip\":\""+newRecyclingTip.getTip()+"\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(newRecyclingTip.getId()))
                .andExpect(jsonPath("$.tip").value(newRecyclingTip.getTip()));
    }

    @Test
    public void testAddRecyclingTips_BadRequest() throws Exception {
        // Arrange
        Long id = 1L;
        RecyclingTips newRecyclingTip = new RecyclingTips();
        newRecyclingTip.setId(id);
        newRecyclingTip.setTip(recyclingTip);
        when(service.addRecyclingTip(any(RecyclingTips.class))).thenReturn(newRecyclingTip);

        mockMvc.perform(post("/api/recycling-tips")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"tip\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateRecyclingTips_Success() throws Exception {
        // Arrange
        Long id = 1L;

        RecyclingTips updateRecyclingTip = new RecyclingTips();
        updateRecyclingTip.setTip(updatedRecyclingTip);
        when(service.updateRecyclingTip(eq(id), any(RecyclingTips.class))).thenReturn(updateRecyclingTip);

        // Act & Assert
        mockMvc.perform(put("/api/recycling-tips/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"tip\":\""+updateRecyclingTip.getTip()+"\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updateRecyclingTip.getId()))
                .andExpect(jsonPath("$.tip").value(updateRecyclingTip.getTip()));
    }

    @Test
    public void testUpdateRecyclingTips_BadRequest() throws Exception {
        // Arrange
        Long id = 1L;
        RecyclingTips updateRecyclingTip = new RecyclingTips();
        updateRecyclingTip.setId(id);
        updateRecyclingTip.setTip(updatedRecyclingTip);
        when(service.updateRecyclingTip(eq(id), any(RecyclingTips.class))).thenReturn(updateRecyclingTip);

        // Act && Assert
        mockMvc.perform(put("/api/recycling-tips/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"tip\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteRecyclingTips_Success() throws Exception {
        // Arrange
        Long id = 1L;

        // Act & Assert
        mockMvc.perform(delete("/api/recycling-tips/{id}", id))
                .andExpect(status().isNoContent());
        verify(service, times(1)).deleteRecyclingTip(id);
    }

    @Test
    public void testDeleteRecyclingTips_NotFound() throws Exception {
        // Arrange
        Long id = 1L;
        doThrow(new NotFoundException("Error 404: Not Found")).when(service).deleteRecyclingTip(id);

        // Act && Assert
        mockMvc.perform(delete("/api/recycling-tips/{id}", id))
                .andExpect(status().isNotFound());
        verify(service, times(1)).deleteRecyclingTip(id);
    }

    @Test
    public void testTipsInvalidEndpoint_NotFound() throws Exception {
        // Act && Assert
        mockMvc.perform(get("/api/invalid-tips-endpoint")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
