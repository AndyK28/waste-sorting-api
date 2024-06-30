package com.enviro.assessment.grad001.andilekhumalo.controller;

import com.enviro.assessment.grad001.andilekhumalo.model.RecyclingTIps;
import com.enviro.assessment.grad001.andilekhumalo.service.RecyclingTipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recycling-tips")
public class RecyclingTipController {
    @Autowired
    private RecyclingTipService service;

    @GetMapping
    public List<RecyclingTIps> getAllRecyclingTips() {
        return service.getAllTips();
    }

    @GetMapping("/{id}")
    public RecyclingTIps getRecyclingTipById(@PathVariable Long id) {
        return service.getTipById(id);
    }

    @PostMapping
    public RecyclingTIps addRecyclingTips(@Validated @RequestBody RecyclingTIps recyclingTips) {
        return service.saveTip(recyclingTips);
    }

    @PutMapping("/{id}")
    public RecyclingTIps updateTips(@PathVariable Long id, @Validated @RequestBody RecyclingTIps recyclingTips) {
        return service.updateTip(id, recyclingTips);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTips(@PathVariable Long id) {
        service.deleteTip(id);
        return ResponseEntity.noContent().build();
    }
}
