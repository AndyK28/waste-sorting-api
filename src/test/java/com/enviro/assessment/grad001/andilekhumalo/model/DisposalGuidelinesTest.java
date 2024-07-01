package com.enviro.assessment.grad001.andilekhumalo.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.*;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class DisposalGuidelinesTest {
    private Validator validator;
    private DisposalGuidelines disposalGuidelines;

    @BeforeEach
    void setUp() {
        disposalGuidelines = new DisposalGuidelines();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterEach
    void tearDown() {
        disposalGuidelines = null;
    }

    @Test
    public void testIdField() {
        // Arrange
        Long id = 1L;

        // Act && Assert
        disposalGuidelines.setId(id);
        assertEquals(id, disposalGuidelines.getId());
    }

    @Test
    public void testGuidelineField() {
        // Arrange
        String guideline = "Dispose plastic bottles in the recycling bin";

        // Act && Assert
        disposalGuidelines.setGuideline(guideline);
        assertEquals(guideline, disposalGuidelines.getGuideline());
    }

    @Test
    public void testGuidelineFieldNotNullOrBlank() {
        // Test with blank guideline
        disposalGuidelines.setGuideline("");
        Set<ConstraintViolation<DisposalGuidelines>> violations = validator.validate(disposalGuidelines);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .map(ConstraintViolation::getMessage)
                .toList()
                .contains("Guideline is mandatory"));

        // Test with null guideline
        disposalGuidelines.setGuideline(null);
        violations = validator.validate(disposalGuidelines);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .map(ConstraintViolation::getMessage)
                .toList()
                .contains("Guideline is mandatory"));

        // Test with valid guideline
        disposalGuidelines.setGuideline("Dispose of electronics in the landfill");
        violations = validator.validate(disposalGuidelines);
        assertTrue(violations.isEmpty());
    }
}
