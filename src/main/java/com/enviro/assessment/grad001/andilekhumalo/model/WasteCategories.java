package com.enviro.assessment.grad001.andilekhumalo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class WasteCategories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Category is mandatory")
    String category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Category is mandatory") String getCategory() {
        return category;
    }

    public void setCategory(@NotBlank(message = "Category is mandatory") String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "WasteCategories{" +
                "id=" + id +
                ", category='" + category + '\'' +
                '}';
    }
}
