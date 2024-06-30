package com.enviro.assessment.grad001.andilekhumalo.service;

import com.enviro.assessment.grad001.andilekhumalo.exception.NotFoundException;
import com.enviro.assessment.grad001.andilekhumalo.model.WasteCategory;
import com.enviro.assessment.grad001.andilekhumalo.repository.WasteCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WasteCategoryService {
    @Autowired
    private WasteCategoryRepository repository;

    public List<WasteCategory> getAllCategories() {
        return repository.findAll();
    }

    public WasteCategory getCategoryById(Long id) {
        Optional<WasteCategory> category = repository.findById(id);
        if (category.isPresent()) {
            return category.get();
        }
        throw new NotFoundException("Category Not Found");
    }

    public  WasteCategory save(WasteCategory wasteCategory) {
        return repository.save(wasteCategory);
    }

    public WasteCategory update(Long id, WasteCategory wasteCategory) {
        WasteCategory existingCategory = getCategoryById(id);
        existingCategory.setName(wasteCategory.getName());
        return repository.save(existingCategory);
    }

    public void delete(Long id) {
        WasteCategory category = getCategoryById(id);
        repository.delete(category);
    }
}
