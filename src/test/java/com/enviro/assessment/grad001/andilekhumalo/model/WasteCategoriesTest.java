package com.enviro.assessment.grad001.andilekhumalo.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.*;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class WasteCategoriesTest {
    private Validator validator;
    private WasteCategories wasteCategory;

    @BeforeEach
    void setUp() {
        wasteCategory = new WasteCategories();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterEach
    void tearDown() {
        wasteCategory = null;
    }

    @Test
    public void testIdField() {
        // Arrange
        Long id = 1L;

        // Act && Assert
        wasteCategory.setId(id);
        assertEquals(id, wasteCategory.getId());
    }

    @Test
    public void testCategoryField() {
        // Arrange
        String category = "Papers";

        // Act && Assert
        wasteCategory.setCategory(category);
        assertEquals(category, wasteCategory.getCategory());
    }

    @Test
    public void testCategoryFieldNotNullOrBlank() {
        // Test with blank category
        wasteCategory.setCategory("");
        Set<ConstraintViolation<WasteCategories>> violations = validator.validate(wasteCategory);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .map(ConstraintViolation::getMessage)
                .toList()
                .contains("Category is mandatory"));

        // Test with a null category
        wasteCategory.setCategory(null);
        violations = validator.validate(wasteCategory);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .map(ConstraintViolation::getMessage)
                .toList()
                .contains("Category is mandatory"));

        // Test with a valid category
        wasteCategory.setCategory("Tin Cans");
        violations = validator.validate(wasteCategory);
        assertTrue(violations.isEmpty());
    }
}
