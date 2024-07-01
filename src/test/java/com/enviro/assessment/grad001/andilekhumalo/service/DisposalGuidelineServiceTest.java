package com.enviro.assessment.grad001.andilekhumalo.service;

import com.enviro.assessment.grad001.andilekhumalo.exception.NotFoundException;
import com.enviro.assessment.grad001.andilekhumalo.model.DisposalGuidelines;
import com.enviro.assessment.grad001.andilekhumalo.repository.DisposalGuidelineRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DisposalGuidelineServiceTest {
    @Mock
    private DisposalGuidelineRepository repository;

    @InjectMocks
    private DisposalGuidelineService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll() {
        // Arrange
        DisposalGuidelines guideline1 = new DisposalGuidelines();
        guideline1.setId(1L);
        guideline1.setGuideline("Dispose of plastics in the blue bin");

        DisposalGuidelines guideline2 = new DisposalGuidelines();
        guideline2.setId(2L);
        guideline2.setGuideline("Dispose of papers in the green bin");

        List<DisposalGuidelines> guidelines = Arrays.asList(guideline1, guideline2);
        when(repository.findAll()).thenReturn(guidelines);

        // Act
        List<DisposalGuidelines> result = service.getAllGuidelines();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Dispose of plastics in the blue bin", result.get(0).getGuideline());
        assertEquals("Dispose of papers in the green bin", result.get(1).getGuideline());
    }

    @Test
    public void testGetGuidelineById() {
        // Arrange
        DisposalGuidelines guideline = new DisposalGuidelines();
        guideline.setId(1L);
        guideline.setGuideline("Dispose of plastics in the blue bin");
        when(repository.findById(1L)).thenReturn(Optional.of(guideline));

        // Act
        DisposalGuidelines result = service.getGuidelineById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Dispose of plastics in the blue bin", result.getGuideline());
    }

    @Test
    public void testGetGuidelineById_NotFound() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            service.getGuidelineById(1L);
        });

        assertEquals("Error 404: Not Found", thrown.getMessage());
    }

    @Test
    public void testAddGuideline() {
        // Arrange
        DisposalGuidelines guideline = new DisposalGuidelines();
        guideline.setGuideline("Dispose of plastics in the blue bin");
        when(repository.save(any(DisposalGuidelines.class))).thenReturn(guideline);

        // Act
        DisposalGuidelines result = service.addGuideline(guideline);

        // Assert
        assertNotNull(result);
        assertEquals("Dispose of plastics in the blue bin", result.getGuideline());
    }

    @Test
    public void testUpdateGuideline() {
        // Arrange
        DisposalGuidelines existingGuideline = new DisposalGuidelines();
        existingGuideline.setId(1L);
        existingGuideline.setGuideline("Dispose of plastics in the blue bin");
        when(repository.findById(1L)).thenReturn(Optional.of(existingGuideline));

        DisposalGuidelines updatedGuideline = new DisposalGuidelines();
        updatedGuideline.setGuideline("Dispose of electronics at designated centers");
        when(repository.save(any(DisposalGuidelines.class))).thenReturn(updatedGuideline);

        // Act
        DisposalGuidelines result = service.updateGuideline(1L, updatedGuideline);

        // Assert
        assertNotNull(result);
        assertEquals("Dispose of electronics at designated centers", result.getGuideline());
    }

    @Test
    public void testUpdateGuideline_NotFound() {
        // Arrange
        DisposalGuidelines updatedGuideline = new DisposalGuidelines();
        updatedGuideline.setGuideline("Dispose of electronics at designated centers");
        when(repository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            service.updateGuideline(1L, updatedGuideline);
        });

        assertEquals("Error 404: Not Found", thrown.getMessage());
    }

    @Test
    public void testDeleteGuideline() {
        // Arrange
        DisposalGuidelines guideline = new DisposalGuidelines();
        guideline.setId(1L);
        guideline.setGuideline("Dispose of plastics in the blue bin");
        when(repository.findById(1L)).thenReturn(Optional.of(guideline));

        // Act
        service.deleteGuideline(1L);

        // Assert
        verify(repository, times(1)).delete(guideline);
    }

    @Test
    public void testDeleteGuideline_NotFound() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            service.deleteGuideline(1L);
        });

        assertEquals("Error 404: Not Found", thrown.getMessage());
    }
}
