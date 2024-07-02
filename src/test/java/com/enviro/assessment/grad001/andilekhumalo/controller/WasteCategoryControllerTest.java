package com.enviro.assessment.grad001.andilekhumalo.controller;

import com.enviro.assessment.grad001.andilekhumalo.exception.NotFoundException;
import com.enviro.assessment.grad001.andilekhumalo.model.WasteCategories;
import com.enviro.assessment.grad001.andilekhumalo.service.WasteCategoryService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WasteCategoryController.class)
@ExtendWith(MockitoExtension.class)
public class WasteCategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WasteCategoryService service;

    WasteCategories category1;
    WasteCategories category2;
    WasteCategories category3;

    @BeforeEach
    void setUp() {
        category1 = new WasteCategories(1L, "Organic Waste");
        category2 = new WasteCategories(2L, "Recyclable Waste");
        category3 = new WasteCategories(3L, "Hazardous Waste");
    }

    @AfterEach
    void tearDown() {
        category1 = null;
        category2 = null;
        category3 = null;
    }

    @Test
    public void testGetAllCategories_Success() throws Exception {
        // Arrange
        WasteCategories category_1 = category1;
        WasteCategories category_2 = category2;
        WasteCategories category_3 = category3;
        when(service.getAllCategories()).thenReturn(Arrays.asList(category_1, category_2, category_3));

        // Act & Assert
        mockMvc.perform(get("/api/waste-categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(category_1.getId()))
                .andExpect(jsonPath("$[0].category").value(category_1.getCategory()))
                .andExpect(jsonPath("$[1].id").value(category_2.getId()))
                .andExpect(jsonPath("$[1].category").value(category_2.getCategory()))
                .andExpect(jsonPath("$[2].id").value(category_3.getId()))
                .andExpect(jsonPath("$[2].category").value(category_3.getCategory()));
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
        Long id = 3L;
        WasteCategories wasteCategory = category3;
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
        WasteCategories newCategory = category2;
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
        WasteCategories newCategory = category2;
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
        WasteCategories existingCategory = category1;
        when(service.getCategoryById(id)).thenReturn(existingCategory);
        WasteCategories updateCategory = category3;
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
        WasteCategories existingCategory = category1;
        when(service.getCategoryById(id)).thenReturn(existingCategory);
        WasteCategories updateCategory = category3;
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
