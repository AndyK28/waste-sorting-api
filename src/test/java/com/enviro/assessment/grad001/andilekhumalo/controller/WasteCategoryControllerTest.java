package com.enviro.assessment.grad001.andilekhumalo.controller;

import com.enviro.assessment.grad001.andilekhumalo.exception.NotFoundException;
import com.enviro.assessment.grad001.andilekhumalo.model.WasteCategories;
import com.enviro.assessment.grad001.andilekhumalo.service.WasteCategoryService;
import org.junit.jupiter.api.*;
import org.mockito.*;
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

@WebMvcTest(WasteCategoryController.class)
public class WasteCategoryControllerTest {
    private String category;
    private String updatedCategory;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WasteCategoryService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        category = "Plastic";
        updatedCategory = "Cardboard";
    }

    @AfterEach
    void tearDown() {
        category = null;
        updatedCategory = null;
    }

    @Test
    public void testGetAllCategories_Success() throws Exception {
        // Arrange
        WasteCategories wasteCategory = new WasteCategories();
        wasteCategory.setId(1L);
        wasteCategory.setCategory(category);
        when(service.getAllCategories()).thenReturn(Collections.singletonList(wasteCategory));

        // Act & Assert
        mockMvc.perform(get("/api/waste-categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(wasteCategory.getId()))
                .andExpect(jsonPath("$[0].category").value(wasteCategory.getCategory()));
    }

    @Test
    public void testGetAllCategories_EmptyList() throws Exception {
        // Arrange
        when(service.getAllCategories()).thenThrow(new NotFoundException("Error 404: Not Found"));

        // Act && Assert
        mockMvc.perform(get("/api/waste-categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetCategoryById_Success() throws Exception {
        // Arrange
        Long id = 1L;
        WasteCategories wasteCategory = new WasteCategories();
        wasteCategory.setId(id);
        wasteCategory.setCategory(category);
        when(service.getCategoryById(id)).thenReturn(wasteCategory);

        // Act & Assert
        mockMvc.perform(get("/api/waste-categories/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(wasteCategory.getId()))
                .andExpect(jsonPath("$.category").value(wasteCategory.getCategory()));
    }

    @Test
    public void testGetCategoryById_NotFound() throws Exception {
        // Arrange
        Long id = 1L;
        when(service.getCategoryById(id)).thenThrow(new NotFoundException("Error 404: Not Found"));

        // Act && Assert
        mockMvc.perform(get("/api/waste-categories/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddCategory_Success() throws Exception {
        // Arrange
        Long id = 1L;
        WasteCategories newCategory = new WasteCategories();
        newCategory.setId(id);
        newCategory.setCategory(category);
        when(service.addCategory(any(WasteCategories.class))).thenReturn(newCategory);

        // Act & Assert
        mockMvc.perform(post("/api/waste-categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"category\":\""+newCategory.getCategory()+"\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(newCategory.getId()))
                .andExpect(jsonPath("$.category").value(newCategory.getCategory()));
    }

    @Test
    public void testAddCategory_BadRequest() throws Exception {
        // Arrange
        Long id = 1L;
        WasteCategories newCategory = new WasteCategories();
        newCategory.setId(id);
        newCategory.setCategory(category);
        when(service.addCategory(any(WasteCategories.class))).thenReturn(newCategory);

        // Act & Assert
        mockMvc.perform(post("/api/waste-categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"category\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateCategory_Success() throws Exception {
        // Arrange
        Long id = 1L;
        WasteCategories updateCategory = new WasteCategories();
        updateCategory.setCategory(updatedCategory);
        when(service.updateCategory(eq(id), any(WasteCategories.class))).thenReturn(updateCategory);

        // Act & Assert
        mockMvc.perform(put("/api/waste-categories/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"category\":\""+updateCategory.getCategory()+"\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updateCategory.getId()))
                .andExpect(jsonPath("$.category").value(updateCategory.getCategory()));
    }

    @Test
    public void testUpdateCategory_BadRequest() throws Exception {
        // Arrange
        Long id = 1L;
        WasteCategories updateCategory = new WasteCategories();
        updateCategory.setCategory(updatedCategory);
        when(service.updateCategory(eq(id), any(WasteCategories.class))).thenReturn(updateCategory);

        // Act & Assert
        mockMvc.perform(put("/api/waste-categories/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"category\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteCategory_Success() throws Exception {
        // Arrange
        Long id = 1L;

        // Act & Assert
        mockMvc.perform(delete("/api/waste-categories/{id}", id))
                .andExpect(status().isNoContent());

        verify(service, times(1)).deleteCategory(id);
    }

    @Test
    public void testCategoryInvalidEndpoint_NotFound() throws Exception {
        // Act && Assert
        mockMvc.perform(get("/api/invalid-category-endpoint")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
