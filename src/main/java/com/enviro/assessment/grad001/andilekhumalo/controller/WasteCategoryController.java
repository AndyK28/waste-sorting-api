package com.enviro.assessment.grad001.andilekhumalo.controller;

import com.enviro.assessment.grad001.andilekhumalo.model.WasteCategories;
import com.enviro.assessment.grad001.andilekhumalo.service.WasteCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/waste-categories")
public class WasteCategoryController {
    @Autowired
    private WasteCategoryService service;

    @GetMapping
    public List<WasteCategories> getAllCategories() {
        return service.getAllCategories();
    }

    @GetMapping("/{id}")
    public WasteCategories getCategoryById(@PathVariable Long id) {
        return service.getCategoryById(id);
    }

    @PostMapping
    public WasteCategories addCategory(@Validated  @RequestBody WasteCategories category) {
        return service.save(category);
    }

    @PutMapping("/{id}")
    public WasteCategories updateCategory(@PathVariable Long id, @Validated @RequestBody WasteCategories category) {
        return service.update(id, category);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
