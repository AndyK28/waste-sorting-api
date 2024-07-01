package com.enviro.assessment.grad001.andilekhumalo.service;

import com.enviro.assessment.grad001.andilekhumalo.exception.NotFoundException;
import com.enviro.assessment.grad001.andilekhumalo.model.WasteCategories;
import com.enviro.assessment.grad001.andilekhumalo.repository.WasteCategoryRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class WasteCategoryServiceTest {
    @Mock
    private WasteCategoryRepository repository;

    @InjectMocks
    private WasteCategoryService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCategories() {
        // Arrange
        WasteCategories category1 = new WasteCategories();
        category1.setId(1L);
        category1.setCategory("Plastic");

        WasteCategories category2 = new WasteCategories();
        category2.setId(2L);
        category2.setCategory("Paper");

        List<WasteCategories> categories = Arrays.asList(category1, category2);
        when(repository.findAll()).thenReturn(categories);

        // Act
        List<WasteCategories> result = service.getAllCategories();

        // Assert
        assertEquals(categories.size(), result.size());
        assertEquals(categories.get(0).getCategory(), result.get(0).getCategory());
        assertEquals(categories.get(1).getCategory(), result.get(1).getCategory());
    }

    @Test
    public void testGetCategoryById() {
        // Arrange
        WasteCategories category = new WasteCategories();
        category.setId(1L);
        category.setCategory("Plastic");
        when(repository.findById(1L)).thenReturn(Optional.of(category));

        // Act
        WasteCategories result = service.getCategoryById(category.getId());

        // Assert
        assertNotNull(result);
        assertEquals(category.getCategory(), result.getCategory());
    }

    @Test
    public void testGetCategoryById_NotFound() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            service.getCategoryById(1L);
        });

        assertEquals("Error 404: Not Found", thrown.getMessage());
    }

    @Test
    public void testSaveCategory() {
        // Arrange
        WasteCategories category = new WasteCategories();
        category.setCategory("Plastic");
        when(repository.save(any(WasteCategories.class))).thenReturn(category);

        // Act
        WasteCategories result = service.save(category);

        // Assert
        assertNotNull(result);
        assertEquals(category.getCategory(), result.getCategory());
    }

    @Test
    public void testUpdateCategory() {
        // Arrange
        WasteCategories existingCategory = new WasteCategories();
        existingCategory.setId(1L);
        existingCategory.setCategory("Plastic");
        when(repository.findById(1L)).thenReturn(Optional.of(existingCategory));

        WasteCategories updatedCategory = new WasteCategories();
        updatedCategory.setCategory("Metal");
        when(repository.save(any(WasteCategories.class))).thenReturn(updatedCategory);

        // Act
        WasteCategories result = service.update(existingCategory.getId(), updatedCategory);

        // Assert
        assertNotNull(result);
        assertEquals(updatedCategory.getCategory(), result.getCategory());
    }

    @Test
    public void testUpdateCategory_NotFound() {
        // Arrange
        WasteCategories updatedCategory = new WasteCategories();
        updatedCategory.setCategory("Metal");
        when(repository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            service.update(1L, updatedCategory);
        });

        assertEquals("Error 404: Not Found", thrown.getMessage());
    }

    @Test
    public void testDeleteCategory() {
        // Arrange
        WasteCategories category = new WasteCategories();
        category.setId(1L);
        category.setCategory("Plastic");
        when(repository.findById(1L)).thenReturn(Optional.of(category));

        // Act
        service.delete(category.getId());

        // Assert
        verify(repository, times(1)).delete(category);
    }

    @Test
    public void testDeleteCategory_NotFound() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            service.delete(1L);
        });

        assertEquals("Error 404: Not Found", thrown.getMessage());
    }
}
