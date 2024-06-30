package com.enviro.assessment.grad001.andilekhumalo.controller;

import com.enviro.assessment.grad001.andilekhumalo.model.RecyclingTips;
import com.enviro.assessment.grad001.andilekhumalo.service.RecyclingTipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recycling-tips")
public class RecyclingTipController {
    @Autowired
    private RecyclingTipService service;

    @GetMapping
    public List<RecyclingTips> getAllRecyclingTips() {
        return service.getAllTips();
    }

    @GetMapping("/{id}")
    public RecyclingTips getRecyclingTipById(@PathVariable Long id) {
        return service.getTipById(id);
    }

    @PostMapping
    public RecyclingTips addRecyclingTips(@Validated @RequestBody RecyclingTips recyclingTips) {
        return service.saveTip(recyclingTips);
    }

    @PutMapping("/{id}")
    public RecyclingTips updateTips(@PathVariable Long id, @Validated @RequestBody RecyclingTips recyclingTips) {
        return service.updateTip(id, recyclingTips);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTips(@PathVariable Long id) {
        service.deleteTip(id);
        return ResponseEntity.noContent().build();
    }
}
