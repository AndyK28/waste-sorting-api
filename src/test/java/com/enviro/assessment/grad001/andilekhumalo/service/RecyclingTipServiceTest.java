package com.enviro.assessment.grad001.andilekhumalo.service;

import com.enviro.assessment.grad001.andilekhumalo.exception.NotFoundException;
import com.enviro.assessment.grad001.andilekhumalo.model.RecyclingTips;
import com.enviro.assessment.grad001.andilekhumalo.repository.RecyclingTipRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RecyclingTipServiceTest {
    @Mock
    private RecyclingTipRepository repository;

    @InjectMocks
    private RecyclingTipService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllRecyclingTips() {
        // Arrange
        RecyclingTips tip1 = new RecyclingTips();
        tip1.setId(1L);
        tip1.setTip("Reduce plastic use");

        RecyclingTips tip2 = new RecyclingTips();
        tip2.setId(2L);
        tip2.setTip("Recycle paper");

        List<RecyclingTips> tips = Arrays.asList(tip1, tip2);
        when(repository.findAll()).thenReturn(tips);

        // Act
        List<RecyclingTips> result = service.getAllRecyclingTips();

        // Assert
        assertEquals(tips.size(), result.size());
        assertEquals(tips.get(0).getTip(), result.get(0).getTip());
        assertEquals(tips.get(1).getTip(), result.get(1).getTip());
    }

    @Test
    public void testGetRecyclingTipById() {
        // Arrange
        RecyclingTips tip = new RecyclingTips();
        tip.setId(1L);
        tip.setTip("Reduce plastic use");
        when(repository.findById(1L)).thenReturn(Optional.of(tip));

        // Act
        RecyclingTips result = service.getRecyclingTipById(tip.getId());

        // Assert
        assertNotNull(result);
        assertEquals(tip.getTip(), result.getTip());
    }

    @Test
    public void testGetRecyclingTipById_NotFound() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            service.getRecyclingTipById(1L);
        });

        assertEquals("Error 404: Not Found", thrown.getMessage());
    }

    @Test
    public void testAddRecyclingTip() {
        // Arrange
        RecyclingTips tip = new RecyclingTips();
        tip.setTip("Reduce plastic use");
        when(repository.save(any(RecyclingTips.class))).thenReturn(tip);

        // Act
        RecyclingTips result = service.addRecyclingTip(tip);

        // Assert
        assertNotNull(result);
        assertEquals(tip.getTip(), result.getTip());
    }

    @Test
    public void testUpdateRecyclingTip() {
        // Arrange
        RecyclingTips existingTip = new RecyclingTips();
        existingTip.setId(1L);
        existingTip.setTip("Reduce plastic use");
        when(repository.findById(existingTip.getId())).thenReturn(Optional.of(existingTip));

        RecyclingTips updatedTip = new RecyclingTips();
        updatedTip.setTip("Recycle paper");
        when(repository.save(any(RecyclingTips.class))).thenReturn(updatedTip);

        // Act
        RecyclingTips result = service.updateRecyclingTip(existingTip.getId(), updatedTip);

        // Assert
        assertNotNull(result);
        assertEquals(updatedTip.getTip(), result.getTip());
    }

    @Test
    public void testUpdateRecyclingTip_NotFound() {
        // Arrange
        RecyclingTips updatedTip = new RecyclingTips();
        updatedTip.setTip("Recycle paper");
        when(repository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            service.updateRecyclingTip(1L, updatedTip);
        });

        assertEquals("Error 404: Not Found", thrown.getMessage());
    }

    @Test
    public void testDeleteRecyclingTip() {
        // Arrange
        RecyclingTips tip = new RecyclingTips();
        tip.setId(1L);
        tip.setTip("Reduce plastic use");
        when(repository.findById(1L)).thenReturn(Optional.of(tip));

        // Act
        service.deleteRecyclingTip(tip.getId());

        // Assert
        verify(repository, times(1)).delete(tip);
    }

    @Test
    public void testDeleteRecyclingTip_NotFound() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            service.deleteRecyclingTip(1L);
        });

        assertEquals("Error 404: Not Found", thrown.getMessage());
    }
}
