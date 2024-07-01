package com.enviro.assessment.grad001.andilekhumalo.service;

import com.enviro.assessment.grad001.andilekhumalo.exception.NotFoundException;
import com.enviro.assessment.grad001.andilekhumalo.model.WasteCategories;
import com.enviro.assessment.grad001.andilekhumalo.repository.WasteCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WasteCategoryService {
    @Autowired
    private WasteCategoryRepository repository;

    public List<WasteCategories> getAllCategories() {
        List<WasteCategories> categories = repository.findAll();
        if (categories.isEmpty()) {
            throw new NotFoundException("Error 404: Not Found");
        }
        return repository.findAll();
    }

    public WasteCategories getCategoryById(Long id) {
        Optional<WasteCategories> category = repository.findById(id);
        if (category.isPresent()) {
            return category.get();
        }
        throw new NotFoundException("Error 404: Not Found");
    }

    public WasteCategories save(WasteCategories wasteCategory) {
        return repository.save(wasteCategory);
    }

    public WasteCategories update(Long id, WasteCategories wasteCategory) {
        WasteCategories existingCategory = getCategoryById(id);
        if (existingCategory == null) {
            throw new NotFoundException("Error 404: Not Found");
        }
        existingCategory.setCategory(wasteCategory.getCategory());
        return repository.save(existingCategory);
    }

    public void delete(Long id) {
        WasteCategories category = getCategoryById(id);
        repository.delete(category);
    }
}
