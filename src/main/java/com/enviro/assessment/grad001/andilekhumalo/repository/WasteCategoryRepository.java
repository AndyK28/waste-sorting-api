package com.enviro.assessment.grad001.andilekhumalo.repository;

import com.enviro.assessment.grad001.andilekhumalo.model.WasteCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WasteCategoryRepository extends JpaRepository<WasteCategories, Long> {
}
