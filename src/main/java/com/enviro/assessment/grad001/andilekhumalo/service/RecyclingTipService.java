package com.enviro.assessment.grad001.andilekhumalo.service;

import com.enviro.assessment.grad001.andilekhumalo.exception.NotFoundException;
import com.enviro.assessment.grad001.andilekhumalo.model.RecyclingTips;
import com.enviro.assessment.grad001.andilekhumalo.repository.RecyclingTipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing recycling tips.
 */
@Service
public class RecyclingTipService {
    private final RecyclingTipRepository repository;

    /**
     * Constructs a new RecyclingTipService with the specified repository.
     *
     * @param repository the repository to be used for managing recycling tips
     */
    @Autowired
    public RecyclingTipService(RecyclingTipRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrieves all recycling tips.
     *
     * @return a list of all recycling tips
     * @throws NotFoundException if no recycling tips are found
     */
    public List<RecyclingTips> getAllRecyclingTips() {
        List<RecyclingTips> recyclingTips = repository.findAll();
        if (recyclingTips.isEmpty()) {
            throw new NotFoundException("Error 404: Tips Not Found");
        }
        return recyclingTips;
    }

    /**
     * Retrieves a recycling tip by its ID.
     *
     * @param id the ID of the recycling tip to retrieve
     * @return the recycling tip with the specified ID
     * @throws NotFoundException if the recycling tip is not found
     */
    public RecyclingTips getRecyclingTipById(Long id) {
        Optional<RecyclingTips> tip = repository.findById(id);
        if (tip.isPresent()) {
            return tip.get();
        }
        throw new NotFoundException("Error 404: Tip with id "+id+" Not Found");
    }

    /**
     * Adds a new recycling tip.
     *
     * @param tip the recycling tip to add
     * @return the newly added recycling tip
     */
    public RecyclingTips addRecyclingTip(RecyclingTips tip) {
        return repository.save(tip);
    }

    /**
     * Updates an existing recycling tip.
     *
     * @param id the ID of the recycling tip to update
     * @param tip the updated recycling tip
     * @return the updated recycling tip
     */
    public RecyclingTips updateRecyclingTip(Long id, RecyclingTips tip) {
        RecyclingTips existingTip = getRecyclingTipById(id);
        existingTip.setTip(tip.getTip());
        return repository.save(existingTip);
    }

    /**
     * Deletes a recycling tip by its ID.
     *
     * @param id the ID of the recycling tip to delete
     */
    public void deleteRecyclingTip(Long id) {
        RecyclingTips tip = getRecyclingTipById(id);
        repository.delete(tip);
    }
}
