package com.enviro.assessment.grad001.andilekhumalo.service;

import com.enviro.assessment.grad001.andilekhumalo.exception.NotFoundException;
import com.enviro.assessment.grad001.andilekhumalo.model.RecyclingTips;
import com.enviro.assessment.grad001.andilekhumalo.repository.RecyclingTipRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class RecyclingTipServiceTest {
    @Mock
    private RecyclingTipRepository repository;

    @InjectMocks
    private RecyclingTipService service;

    private RecyclingTips tip1;
    private RecyclingTips tip2;
    private RecyclingTips tip3;

    @BeforeEach
    public void setUp() {
        tip1 = new RecyclingTips(1L, "Reduce, Reuse, Recycle");
        tip2 = new RecyclingTips(2L, "Educate Yourself and Others");
        tip3 = new RecyclingTips(3L, "Buy Recycled Products");
    }

    @AfterEach
    public void tearDown() {
        tip1 = null;
        tip2 = null;
        tip3 = null;
    }

    @Test
    public void testGetAllRecyclingTips() {
        // Arrange
        List<RecyclingTips> tips = Arrays.asList(tip1, tip2, tip3);
        when(repository.findAll()).thenReturn(tips);

        // Act
        List<RecyclingTips> result = service.getAllRecyclingTips();

        // Assert
        assertEquals(tips.size(), result.size());
        assertEquals(tips.get(0).getTip(), result.get(0).getTip());
        assertEquals(tips.get(1).getTip(), result.get(1).getTip());
        assertEquals(tips.get(2).getTip(), result.get(2).getTip());
    }

    @Test
    public void testGetRecyclingTipById() {
        // Arrange
        Long id = 1L;
        RecyclingTips tip = tip1;
        when(repository.findById(id)).thenReturn(Optional.of(tip));

        // Act
        RecyclingTips result = service.getRecyclingTipById(tip.getId());

        // Assert
        assertNotNull(result);
        assertEquals(tip.getTip(), result.getTip());
    }

    @Test
    public void testGetRecyclingTipById_NotFound() {
        // Arrange
        Long id = 6L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            service.getRecyclingTipById(id);
        });
        assertEquals("Error 404: Tip with id "+id+" Not Found", thrown.getMessage());
    }

    @Test
    public void testAddRecyclingTip() {
        // Arrange
        RecyclingTips tip = tip1;
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
        Long id = 3L;
        RecyclingTips existingTip = tip3;
        when(repository.findById(id)).thenReturn(Optional.of(existingTip));
        RecyclingTips updatedTip = tip2;
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
        Long id = 7L;
        RecyclingTips updatedTip = tip3;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            service.updateRecyclingTip(id, updatedTip);
        });
        assertEquals("Error 404: Tip with id "+id+" Not Found", thrown.getMessage());
    }

    @Test
    public void testDeleteRecyclingTip() {
        // Arrange
        Long id = 2L;
        RecyclingTips tip = tip2;
        when(repository.findById(id)).thenReturn(Optional.of(tip));

        // Act
        service.deleteRecyclingTip(tip.getId());

        // Assert
        verify(repository, times(1)).delete(tip);
    }

    @Test
    public void testDeleteRecyclingTip_NotFound() {
        // Arrange
        Long id = 6L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            service.deleteRecyclingTip(id);
        });
        assertEquals("Error 404: Tip with id "+id+" Not Found", thrown.getMessage());
    }
}
