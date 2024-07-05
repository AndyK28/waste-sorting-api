package com.enviro.assessment.grad001.andilekhumalo.service;

import com.enviro.assessment.grad001.andilekhumalo.exception.NotFoundException;
import com.enviro.assessment.grad001.andilekhumalo.model.WasteCategories;
import com.enviro.assessment.grad001.andilekhumalo.repository.WasteCategoryRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WasteCategoryServiceTest {
    @Mock
    private WasteCategoryRepository repository;

    @InjectMocks
    private WasteCategoryService service;

    private WasteCategories category1;
    private WasteCategories category2;

    @BeforeEach
    void setUp() {
        category1 = new WasteCategories(1L, "Organic Waste");
        category2 = new WasteCategories(2L, "Recyclable Waste");
    }

    @AfterEach
    void tearDown() {
        category1 = null;
        category2 = null;
    }

    @Test
    public void testGetAllCategories() {
        // Arrange
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
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.of(category1));

        // Act
        WasteCategories result = service.getCategoryById(category1.getId());

        // Assert
        assertNotNull(result);
        assertEquals(category1.getCategory(), result.getCategory());
    }

    @Test
    public void testGetCategoryById_NotFound() {
        // Arrange
        Long id = 6L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            service.getCategoryById(id);
        });
        assertEquals("Error 404: Category with id \"+id+\" Not Found", thrown.getMessage());
    }

    @Test
    public void testAddCategory() {
        // Arrange
        WasteCategories category = category2;
        when(repository.save(any(WasteCategories.class))).thenReturn(category);

        // Act
        WasteCategories result = service.addCategory(category);

        // Assert
        assertNotNull(result);
        assertEquals(category.getCategory(), result.getCategory());
    }

    @Test
    public void testUpdateCategory() {
        // Arrange
        Long id = 1L;
        WasteCategories existingCategory = category1;
        when(repository.findById(id)).thenReturn(Optional.of(existingCategory));

        WasteCategories updatedCategory = category2;
        when(repository.save(any(WasteCategories.class))).thenReturn(updatedCategory);

        // Act
        WasteCategories result = service.updateCategory(category1.getId(), updatedCategory);

        // Assert
        assertNotNull(result);
        assertEquals(updatedCategory.getCategory(), result.getCategory());
    }

    @Test
    public void testUpdateCategory_NotFound() {
        // Arrange
        Long id = 5L;
        WasteCategories category = category2;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            service.updateCategory(id, category);
        });
        assertEquals("Error 404: Category with id "+id+" Not Found", thrown.getMessage());
    }

    @Test
    public void testDeleteCategory() {
        // Arrange
        Long id = 2L;
        WasteCategories category = category2;
        when(repository.findById(id)).thenReturn(Optional.of(category));

        // Act
        service.deleteCategory(category.getId());

        // Assert
        verify(repository, times(1)).delete(category);
    }

    @Test
    public void testDeleteCategory_NotFound() {
        Long id = 5L;
        // Arrange
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            service.deleteCategory(id);
        });
        assertEquals("Error 404: Category with id "+id+" Not Found", thrown.getMessage());
    }
}
