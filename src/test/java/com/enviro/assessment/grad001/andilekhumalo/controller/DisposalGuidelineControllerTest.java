package com.enviro.assessment.grad001.andilekhumalo.controller;

import com.enviro.assessment.grad001.andilekhumalo.exception.NotFoundException;
import com.enviro.assessment.grad001.andilekhumalo.model.DisposalGuidelines;
import com.enviro.assessment.grad001.andilekhumalo.service.DisposalGuidelineService;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class DisposalGuidelineControllerTest {
    private MockMvc mockMvc;
    private String guideline;
    private String updatedGuideline;

    @Mock
    private DisposalGuidelineService service;

    @InjectMocks
    private DisposalGuidelineController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        guideline = "Dispose plastic bottles in the recycling bin";
        updatedGuideline = "Recycle cardboard boxes in the designated bin";
    }

    @AfterEach
    void tearDown() {
        guideline = null;
        updatedGuideline = null;
    }

    @Test
    public void testGetAllGuidelines_Success() throws Exception {
        // Arrange
        DisposalGuidelines disposalGuidelines = new DisposalGuidelines();
        disposalGuidelines.setId(1L);
        disposalGuidelines.setGuideline(guideline);
        when(service.getAllGuidelines()).thenReturn(Collections.singletonList(disposalGuidelines));

        // Act & Assert
        mockMvc.perform(get("/api/disposal-guidelines")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].guideline").value(disposalGuidelines.getGuideline()));
    }

    @Test
    public void testGetAllGuidelines_EmptyList() throws Exception {
        // Arrange
        when(service.getAllGuidelines()).thenThrow(new NotFoundException("Error 404: Not Found"));

        // Act
        mockMvc.perform(get("/api/disposal-guidelines")
                        .contentType(MediaType.APPLICATION_JSON))
                // Assert
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetGuidelineById_Success() throws Exception {
        // Arrange
        Long id = 1L;
        DisposalGuidelines disposalGuidelines = new DisposalGuidelines();
        disposalGuidelines.setId(id);
        disposalGuidelines.setGuideline(guideline);
        when(service.getGuidelineById(id)).thenReturn(disposalGuidelines);

        // Act & Assert
        mockMvc.perform(get("/api/disposal-guidelines/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.guideline").value(disposalGuidelines.getGuideline()));
    }

    @Test
    public void testGetGuidelineById_NotFound() throws Exception {
        // Arrange
        Long id = 1L;
        when(service.getGuidelineById(id)).thenThrow(new NotFoundException("Error 404: Not Found"));

        // Act
        mockMvc.perform(get("/api/disposal-guidelines/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                // Assert
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddGuideline_Success() throws Exception {
        // Arrange
        Long id = 1L;
        DisposalGuidelines newGuideline = new DisposalGuidelines();
        newGuideline.setId(id);
        newGuideline.setGuideline(guideline);
        when(service.addGuideline(any(DisposalGuidelines.class))).thenReturn(newGuideline);

        // Act & Assert
        mockMvc.perform(post("/api/disposal-guidelines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"guideline\":\""+newGuideline.getGuideline()+"\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.guideline").value(newGuideline.getGuideline()));
    }

    @Test
    public void testAddGuideline_BadRequest() throws Exception {
        // Arrange
        Long id = 1L;
        DisposalGuidelines newGuideline = new DisposalGuidelines();
        newGuideline.setId(id);
        newGuideline.setGuideline(guideline);
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
        DisposalGuidelines updateGuideline = new DisposalGuidelines();
        updateGuideline.setGuideline(updatedGuideline);
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
        DisposalGuidelines updateGuideline = new DisposalGuidelines();
        updateGuideline.setGuideline(updatedGuideline);
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
