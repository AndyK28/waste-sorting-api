package com.enviro.assessment.grad001.andilekhumalo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class RecyclingTIps {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tip is mandatory")
    private String tip;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Tip is mandatory") String getTip() {
        return tip;
    }

    public void setTip(@NotBlank(message = "Tip is mandatory") String tip) {
        this.tip = tip;
    }
}
