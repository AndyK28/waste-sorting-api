package com.enviro.assessment.grad001.andilekhumalo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class DisposalGuidelines {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank(message = "Guideline is mandatory")
    String guideline;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Guideline is mandatory") String getGuideline() {
        return guideline;
    }

    public void setGuideline(@NotBlank(message = "Guideline is mandatory") String guideline) {
        this.guideline = guideline;
    }

    @Override
    public String toString() {
        return "DisposalGuidelines{" +
                "id=" + id +
                ", guideline='" + guideline + '\'' +
                '}';
    }
}
