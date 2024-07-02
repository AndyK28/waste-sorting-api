package com.enviro.assessment.grad001.andilekhumalo.service;

import com.enviro.assessment.grad001.andilekhumalo.exception.NotFoundException;
import com.enviro.assessment.grad001.andilekhumalo.model.WasteCategories;
import com.enviro.assessment.grad001.andilekhumalo.repository.WasteCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing waste categories.
 */
@Service
public class WasteCategoryService {
    private final WasteCategoryRepository repository;

    /**
     * Constructs a new WasteCategoryService with the specified repository.
     *
     * @param repository the repository to be used for managing waste categories
     */
    @Autowired
    public WasteCategoryService(WasteCategoryRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrieves all waste categories.
     *
     * @return a list of all waste categories
     * @throws NotFoundException if no waste categories are found
     */
    public List<WasteCategories> getAllCategories() {
        List<WasteCategories> categories = repository.findAll();
        if (categories.isEmpty()) {
            throw new NotFoundException("Error 404: Not Found");
        }
        return categories;
    }

    /**
     * Retrieves a waste category by its ID.
     *
     * @param id the ID of the waste category to retrieve
     * @return the waste category with the specified ID
     * @throws NotFoundException if the waste category is not found
     */
    public WasteCategories getCategoryById(Long id) {
        Optional<WasteCategories> category = repository.findById(id);
        if (category.isPresent()) {
            return category.get();
        }
        throw new NotFoundException("Error 404: Not Found");
    }

    /**
     * Adds a new waste category.
     *
     * @param wasteCategory the waste category to add
     * @return the newly added waste category
     */
    public WasteCategories addCategory(WasteCategories wasteCategory) {
        return repository.save(wasteCategory);
    }

    /**
     * Updates an existing waste category.
     *
     * @param id the ID of the waste category to update
     * @param wasteCategory the updated waste category
     * @return the updated waste category
     */
    public WasteCategories updateCategory(Long id, WasteCategories wasteCategory) {
        WasteCategories existingCategory = getCategoryById(id);
        existingCategory.setCategory(wasteCategory.getCategory());
        return repository.save(existingCategory);
    }

    /**
     * Deletes a waste category by its ID.
     *
     * @param id the ID of the waste category to delete
     */
    public void deleteCategory(Long id) {
        WasteCategories category = getCategoryById(id);
        repository.delete(category);
    }
}
