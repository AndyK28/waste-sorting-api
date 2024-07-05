package com.enviro.assessment.grad001.andilekhumalo.service;

import com.enviro.assessment.grad001.andilekhumalo.exception.NotFoundException;
import com.enviro.assessment.grad001.andilekhumalo.model.DisposalGuidelines;
import com.enviro.assessment.grad001.andilekhumalo.repository.DisposalGuidelineRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DisposalGuidelineServiceTest {
    @Mock
    private DisposalGuidelineRepository repository;

    @InjectMocks
    private DisposalGuidelineService service;

    private DisposalGuidelines guideline1;
    private DisposalGuidelines guideline2;
    private DisposalGuidelines guideline3;

    @BeforeEach
    public void setUp() {
        guideline1 = new DisposalGuidelines(1L, "Separate materials like paper, plastic, glass, and metal " +
                "from general waste and follow local recycling guidelines to ensure proper recycling.");
        guideline2 = new DisposalGuidelines(2L, "Dispose of organic waste such as food scraps " +
                "and yard trimmings in a compost bin to reduce landfill waste and create nutrient-rich soil.");
        guideline3 = new DisposalGuidelines(3L, "Dispose of non-recyclable and non-compostable waste "  +
                "in designated landfill bins, focusing on minimizing landfill waste through effective recycling and composting practices.");
    }

    @AfterEach
    public void tearDown() {
        guideline1 = null;
        guideline2 = null;
        guideline3 = null;
    }

    @Test
    public void testGetAll() {
        // Arrange
        List<DisposalGuidelines> guidelines = Arrays.asList(guideline1, guideline2, guideline3);
        when(repository.findAll()).thenReturn(guidelines);

        // Act
        List<DisposalGuidelines> result = service.getAllGuidelines();

        // Assert
        List<String> expectedResult = Arrays.asList(guideline1.getGuideline(), guideline2.getGuideline(), guideline3.getGuideline());
        assertEquals(expectedResult.size(), result.size());
        assertEquals(expectedResult.get(0), result.get(0).getGuideline());
        assertEquals(expectedResult.get(1), result.get(1).getGuideline());
        assertEquals(expectedResult.get(2), result.get(2).getGuideline());
    }

    @Test
    public void testGetGuidelineById() {
        // Arrange
        Long id = 3L;
        DisposalGuidelines newGuideline = guideline3;
        when(repository.findById(id)).thenReturn(Optional.of(newGuideline));

        // Act
        DisposalGuidelines result = service.getGuidelineById(id);

        // Assert
        assertNotNull(result);
        assertEquals(newGuideline.getGuideline(), result.getGuideline());
    }

    @Test
    public void testGetGuidelineById_NotFound() {
        // Arrange
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> service.getGuidelineById(id));
        assertEquals("Error 404: Guideline with id "+id+" Not Found", thrown.getMessage());
    }

    @Test
    public void testAddGuideline() {
        // Arrange
        DisposalGuidelines newGuideline = guideline2;
        when(repository.save(any(DisposalGuidelines.class))).thenReturn(newGuideline);

        // Act
        DisposalGuidelines result = service.addGuideline(newGuideline);

        // Assert
        assertNotNull(result);
        assertEquals(newGuideline.getGuideline(), result.getGuideline());
    }

    @Test
    public void testUpdateGuideline() {
        // Arrange
        Long id = 1L;
        DisposalGuidelines existingGuideline = guideline1;
        when(repository.findById(id)).thenReturn(Optional.of(existingGuideline));

        DisposalGuidelines updatedGuideline = guideline3;
        when(repository.save(any(DisposalGuidelines.class))).thenReturn(updatedGuideline);

        // Act
        DisposalGuidelines result = service.updateGuideline(existingGuideline.getId(), updatedGuideline);

        // Assert
        assertNotNull(result);
        assertEquals(updatedGuideline.getGuideline(), result.getGuideline());
    }

    @Test
    public void testUpdateGuideline_NotFound() {
        // Arrange
        Long id = 8L;
        DisposalGuidelines updatedGuideline = guideline2;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> service.updateGuideline(id, updatedGuideline));
        assertEquals("Error 404: Guideline with id "+id+" Not Found", thrown.getMessage());
    }

    @Test
    public void testDeleteGuideline() {
        // Arrange
        Long id = 1L;
        DisposalGuidelines guideline = guideline1;
        when(repository.findById(id)).thenReturn(Optional.of(guideline));

        // Act
        service.deleteGuideline(id);

        // Assert
        verify(repository, times(1)).delete(guideline);
    }

    @Test
    public void testDeleteGuideline_NotFound() {
        // Arrange
        Long id = 6L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> service.deleteGuideline(id));
        assertEquals("Error 404: Guideline with id "+id+" Not Found", thrown.getMessage());
    }
}
