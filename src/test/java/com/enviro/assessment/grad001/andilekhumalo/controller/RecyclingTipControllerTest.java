package com.enviro.assessment.grad001.andilekhumalo.controller;

import com.enviro.assessment.grad001.andilekhumalo.exception.NotFoundException;
import com.enviro.assessment.grad001.andilekhumalo.model.RecyclingTips;
import com.enviro.assessment.grad001.andilekhumalo.service.RecyclingTipService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RecyclingTipController.class)
@ExtendWith(MockitoExtension.class)
public class RecyclingTipControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecyclingTipService service;

    private RecyclingTips tip1;
    private RecyclingTips tip2;

    @BeforeEach
    void setUp() {
        tip1 = new RecyclingTips(1L, "Recycle plastic bottles in the recycling bin");
        tip2 = new RecyclingTips(2L, "Recycle cardboard boxes in the designated bin");
    }

    @AfterEach
    void tearDown() {
        tip1 = null;
        tip2 = null;
    }

    @Test
    public void testGetAllRecyclingTips_Success() throws Exception {
        // Arrange
        RecyclingTips tip_1 = tip1;
        RecyclingTips tip_2 = tip2;
        when(service.getAllRecyclingTips()).thenReturn(Arrays.asList(tip_1, tip_2));

        // Act & Assert
        mockMvc.perform(get("/api/recycling-tips")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(tip_1.getId()))
                .andExpect(jsonPath("$[0].tip").value(tip_1.getTip()))
                .andExpect(jsonPath("$[1].id").value(tip_2.getId()))
                .andExpect(jsonPath("$[1].tip").value(tip_2.getTip()));
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
        Long id = 2L;
        RecyclingTips recyclingTips = tip2;
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
        RecyclingTips newRecyclingTip = tip2;
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
        RecyclingTips newRecyclingTip = tip2;
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
        RecyclingTips existingRecyclingTip = tip1;
        when(service.getRecyclingTipById(id)).thenReturn(existingRecyclingTip);
        RecyclingTips updateRecyclingTip = tip2;
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
        RecyclingTips existingRecyclingTip = tip1;
        when(service.updateRecyclingTip(eq(id), any(RecyclingTips.class))).thenReturn(existingRecyclingTip);
        RecyclingTips updateRecyclingTip = tip2;
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
