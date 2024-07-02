package com.enviro.assessment.grad001.andilekhumalo.controller;

import com.enviro.assessment.grad001.andilekhumalo.exception.NotFoundException;
import com.enviro.assessment.grad001.andilekhumalo.model.DisposalGuidelines;
import com.enviro.assessment.grad001.andilekhumalo.service.DisposalGuidelineService;
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

@WebMvcTest(DisposalGuidelineController.class)
@ExtendWith(MockitoExtension.class)
public class DisposalGuidelineControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DisposalGuidelineService service;

    private DisposalGuidelines guideline1;
    private DisposalGuidelines guideline2;

    @BeforeEach
    void setUp() {
        guideline1 = new DisposalGuidelines(1L, "Dispose plastic bottles in the recycling bin");
        guideline2 = new DisposalGuidelines(2L, "Recycle cardboard boxes in the designated bin");
    }

    @AfterEach
    void tearDown() {
        guideline1 = null;
        guideline2 = null;
    }

    @Test
    public void testGetAllGuidelines_Success() throws Exception {
        // Arrange
        DisposalGuidelines guideline_1 = guideline1;
        DisposalGuidelines guideline_2 = guideline2;
        when(service.getAllGuidelines()).thenReturn(Arrays.asList(guideline_1, guideline_2));

        // Act & Assert
        mockMvc.perform(get("/api/disposal-guidelines")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(guideline_1.getId()))
                .andExpect(jsonPath("$[0].guideline").value(guideline_1.getGuideline()))
                .andExpect(jsonPath("$[1].id").value(guideline_2.getId()))
                .andExpect(jsonPath("$[1].guideline").value(guideline_2.getGuideline()));
    }

    @Test
    public void testGetAllGuidelines_EmptyList() throws Exception {
        // Arrange
        when(service.getAllGuidelines()).thenThrow(new NotFoundException("Error 404: Not Found"));

        // Act && Assert
        mockMvc.perform(get("/api/disposal-guidelines")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetGuidelineById_Success() throws Exception {
        // Arrange
        Long id = 1L;
        DisposalGuidelines disposalGuideline = guideline1;
        when(service.getGuidelineById(id)).thenReturn(disposalGuideline);

        // Act & Assert
        mockMvc.perform(get("/api/disposal-guidelines/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(disposalGuideline.getId()))
                .andExpect(jsonPath("$.guideline").value(disposalGuideline.getGuideline()));
    }

    @Test
    public void testGetGuidelineById_NotFound() throws Exception {
        // Arrange
        Long id = 8L;
        when(service.getGuidelineById(id)).thenThrow(new NotFoundException("Error 404: Not Found"));

        // Act && Assert
        mockMvc.perform(get("/api/disposal-guidelines/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddGuideline_Success() throws Exception {
        // Arrange
        DisposalGuidelines newGuideline = guideline2;
        when(service.addGuideline(any(DisposalGuidelines.class))).thenReturn(newGuideline);

        // Act & Assert
        mockMvc.perform(post("/api/disposal-guidelines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"guideline\":\""+newGuideline.getGuideline()+"\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(newGuideline.getId()))
                .andExpect(jsonPath("$.guideline").value(newGuideline.getGuideline()));
    }

    @Test
    public void testAddGuideline_BadRequest() throws Exception {
        // Arrange
        DisposalGuidelines newGuideline = guideline2;
        when(service.addGuideline(any(DisposalGuidelines.class))).thenReturn(newGuideline);

        // Act & Assert
        mockMvc.perform(post("/api/disposal-guidelines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"guideline\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateGuideline_Success() throws Exception {
        // Arrange
        Long id = 1L;
        DisposalGuidelines existingGuideline = guideline1;
        when(service.updateGuideline(id, existingGuideline)).thenReturn(existingGuideline);
        DisposalGuidelines updateGuideline = guideline2;
        when(service.updateGuideline(eq(id), any(DisposalGuidelines.class))).thenReturn(updateGuideline);

        // Act & Assert
        mockMvc.perform(put("/api/disposal-guidelines/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"guideline\":\""+updateGuideline.getGuideline()+"\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updateGuideline.getId()))
                .andExpect(jsonPath("$.guideline").value(updateGuideline.getGuideline()));
    }

    @Test
    public void testUpdateGuideline_BadRequest() throws Exception {
        // Arrange
        Long id = 1L;
        DisposalGuidelines updateGuideline = guideline1;
        when(service.updateGuideline(eq(id), any(DisposalGuidelines.class))).thenReturn(updateGuideline);

        // Act & Assert
        mockMvc.perform(put("/api/disposal-guidelines/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"guideline\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteGuideline_Success() throws Exception {
        // Arrange
        Long id = 1L;

        // Act & Assert
        mockMvc.perform(delete("/api/disposal-guidelines/{id}", id))
                .andExpect(status().isNoContent());
        verify(service, times(1)).deleteGuideline(id);
    }

    @Test
    public void testDeleteGuideline_NotFound() throws Exception {
        // Arrange
        Long id = 1L;
        doThrow(new NotFoundException("Error 404: Not Found")).when(service).deleteGuideline(id);

        // Act & Assert
        mockMvc.perform(delete("/api/disposal-guidelines/{id}", id))
                .andExpect(status().isNotFound());
        verify(service, times(1)).deleteGuideline(id);
    }

    @Test
    public void testGuidelineInvalidEndpoint_NotFound() throws Exception {
        // Act && Assert
        mockMvc.perform(get("/api/invalid-guideline-endpoint")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
