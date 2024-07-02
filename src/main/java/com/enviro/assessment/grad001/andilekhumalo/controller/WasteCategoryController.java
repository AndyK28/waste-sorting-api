package com.enviro.assessment.grad001.andilekhumalo.controller;

import com.enviro.assessment.grad001.andilekhumalo.model.WasteCategories;
import com.enviro.assessment.grad001.andilekhumalo.service.WasteCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing waste categories.
 */
@RestController
@RequestMapping("/api/waste-categories")
public class WasteCategoryController {
    private final WasteCategoryService service;

    /**
     * Constructs a new WasteCategoryController with the specified service.
     *
     * @param service the service to be used for managing waste categories
     */
    @Autowired
    public WasteCategoryController(WasteCategoryService service) {
        this.service = service;
    }

    /**
     * Retrieves all waste categories.
     *
     * @return a ResponseEntity containing the list of all waste categories and an HTTP status code
     */
    @GetMapping
    public ResponseEntity<List<WasteCategories>> getAllCategories() {
        List<WasteCategories> categories = service.getAllCategories();
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }

    /**
     * Retrieves a waste category by its ID.
     *
     * @param id the ID of the waste category to retrieve
     * @return a ResponseEntity containing the waste category and an HTTP status code
     */
    @GetMapping("/{id}")
    public ResponseEntity<WasteCategories> getCategoryById(@PathVariable Long id) {
        WasteCategories category = service.getCategoryById(id);
        return ResponseEntity.status(HttpStatus.OK).body(category);
    }

    /**
     * Adds a new waste category.
     *
     * @param category the waste category to add
     * @return a ResponseEntity containing the newly added waste category and an HTTP status code
     */
    @PostMapping
    public ResponseEntity<WasteCategories> addCategory(@Validated @RequestBody WasteCategories category) {
        WasteCategories categoryAdded = service.addCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryAdded);
    }

    /**
     * Updates an existing waste category.
     *
     * @param id the ID of the waste category to update
     * @param category the updated waste category
     * @return a ResponseEntity containing the updated waste category and an HTTP status code
     */
    @PutMapping("/{id}")
    public ResponseEntity<WasteCategories> updateCategory(@PathVariable Long id, @Validated @RequestBody WasteCategories category) {
        WasteCategories categoryUpdated = service.updateCategory(id, category);
        return ResponseEntity.status(HttpStatus.OK).body(categoryUpdated);
    }

    /**
     * Deletes a waste category by its ID.
     *
     * @param id the ID of the waste category to delete
     * @return a ResponseEntity with no content and an HTTP status code
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        service.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
