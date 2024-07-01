package com.enviro.assessment.grad001.andilekhumalo.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.*;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class RecyclingTipsTest {
    private Validator validator;
    private RecyclingTips recyclingTip;

    @BeforeEach
    void setUp() {
        recyclingTip = new RecyclingTips();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterEach
    void tearDown() {
        recyclingTip = null;
    }

    @Test
    public void testIdField() {
        // Arrange
        Long id = 1L;

        // Act && Assert
        recyclingTip.setId(id);
        assertEquals(id, recyclingTip.getId());
    }

    @Test
    public void testTipField() {
        // Arrange
        String tip = "Separate plastic by type for better recycling";

        // Act && Assert
        recyclingTip.setTip(tip);
        assertEquals(tip, recyclingTip.getTip());
    }

    @Test
    public void testTipFieldNotNullOrBlank() {
        // Test with blank tip
        recyclingTip.setTip("");
        Set<ConstraintViolation<RecyclingTips>> violations = validator.validate(recyclingTip);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .map(ConstraintViolation::getMessage)
                .toList()
                .contains("Tip is mandatory"));

        // Test with null tip
        recyclingTip.setTip(null);
        violations = validator.validate(recyclingTip);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .map(ConstraintViolation::getMessage)
                .toList()
                .contains("Tip is mandatory"));

        // Test with valid tip
        recyclingTip.setTip("Use plastic bags for groceries");
        violations = validator.validate(recyclingTip);
        assertTrue(violations.isEmpty());
    }
}
