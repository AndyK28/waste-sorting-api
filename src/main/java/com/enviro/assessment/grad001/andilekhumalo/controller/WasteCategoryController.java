package com.enviro.assessment.grad001.andilekhumalo.controller;

import com.enviro.assessment.grad001.andilekhumalo.model.WasteCategories;
import com.enviro.assessment.grad001.andilekhumalo.service.WasteCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/waste-categories")
public class WasteCategoryController {
    private final WasteCategoryService service;

    @Autowired
    public WasteCategoryController(WasteCategoryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<WasteCategories>> getAllCategories() {
        List<WasteCategories> categories = service.getAllCategories();
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WasteCategories> getCategoryById(@PathVariable Long id) {
        WasteCategories category = service.getCategoryById(id);
        return ResponseEntity.status(HttpStatus.OK).body(category);
    }

    @PostMapping
    public ResponseEntity<WasteCategories> addCategory(@Validated  @RequestBody WasteCategories category) {
        WasteCategories categoryAdded = service.addCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryAdded);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WasteCategories> updateCategory(@PathVariable Long id, @Validated @RequestBody WasteCategories category) {
        WasteCategories categoryUpdated = service.updateCategory(id, category);
        return ResponseEntity.status(HttpStatus.OK).body(categoryUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        service.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
