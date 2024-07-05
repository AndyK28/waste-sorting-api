package com.enviro.assessment.grad001.andilekhumalo.service;

import com.enviro.assessment.grad001.andilekhumalo.exception.NotFoundException;
import com.enviro.assessment.grad001.andilekhumalo.model.DisposalGuidelines;
import com.enviro.assessment.grad001.andilekhumalo.repository.DisposalGuidelineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing disposal guidelines.
 */
@Service
public class DisposalGuidelineService {
    private final DisposalGuidelineRepository repository;

    /**
     * Constructs a new DisposalGuidelineService with the specified repository.
     *
     * @param repository the repository to be used for managing disposal guidelines
     */
    @Autowired
    public DisposalGuidelineService(DisposalGuidelineRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrieves all disposal guidelines.
     *
     * @return a list of all disposal guidelines
     * @throws NotFoundException if no guidelines are found
     */
    public List<DisposalGuidelines> getAllGuidelines() {
        List<DisposalGuidelines> guidelines = repository.findAll();
        if (guidelines.isEmpty()) {
            throw new NotFoundException("Error 404: Guidelines Not Found");
        }
        return guidelines;
    }

    /**
     * Retrieves a disposal guideline by its ID.
     *
     * @param id the ID of the disposal guideline to retrieve
     * @return the disposal guideline with the specified ID
     * @throws NotFoundException if the guideline is not found
     */
    public DisposalGuidelines getGuidelineById(Long id) {
        Optional<DisposalGuidelines> disposalGuideline = repository.findById(id);
        if (disposalGuideline.isPresent()) {
            return disposalGuideline.get();
        }
        throw new NotFoundException("Error 404: Guideline with id "+id+" Not Found");
    }

    /**
     * Adds a new disposal guideline.
     *
     * @param disposalGuideline the disposal guideline to add
     * @return the newly added disposal guideline
     */
    public DisposalGuidelines addGuideline(DisposalGuidelines disposalGuideline) {
        return repository.save(disposalGuideline);
    }

    /**
     * Updates an existing disposal guideline.
     *
     * @param id the ID of the disposal guideline to update
     * @param disposalGuideline the updated disposal guideline
     * @return the updated disposal guideline
     */
    public DisposalGuidelines updateGuideline(Long id, DisposalGuidelines disposalGuideline) {
        DisposalGuidelines existingGuideline = getGuidelineById(id);
        existingGuideline.setGuideline(disposalGuideline.getGuideline());
        return repository.save(existingGuideline);
    }

    /**
     * Deletes a disposal guideline by its ID.
     *
     * @param id the ID of the disposal guideline to delete
     */
    public void deleteGuideline(Long id) {
        DisposalGuidelines disposalGuideline = getGuidelineById(id);
        repository.delete(disposalGuideline);
    }
}
