package com.enviro.assessment.grad001.andilekhumalo.controller;

import com.enviro.assessment.grad001.andilekhumalo.model.RecyclingTips;
import com.enviro.assessment.grad001.andilekhumalo.service.RecyclingTipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recycling-tips")
public class RecyclingTipController {
    private final RecyclingTipService service;

    @Autowired
    public RecyclingTipController(RecyclingTipService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<RecyclingTips>> getAllRecyclingTips() {
        List<RecyclingTips> tips = service.getAllRecyclingTips();
        return ResponseEntity.status(HttpStatus.OK).body(tips);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecyclingTips> getRecyclingTipById(@PathVariable Long id) {
        RecyclingTips tip = service.getRecyclingTipById(id);
        return ResponseEntity.status(HttpStatus.OK).body(tip);
    }

    @PostMapping
    public ResponseEntity<RecyclingTips> addRecyclingTips(@Validated @RequestBody RecyclingTips recyclingTips) {
        RecyclingTips tip = service.addRecyclingTip(recyclingTips);
        return ResponseEntity.status(HttpStatus.CREATED).body(tip);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecyclingTips> updateRecyclingTips(@PathVariable Long id, @Validated @RequestBody RecyclingTips recyclingTips) {
        RecyclingTips tips = service.updateRecyclingTip(id, recyclingTips);
        return ResponseEntity.status(HttpStatus.OK).body(tips);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecyclingTips(@PathVariable Long id) {
        service.deleteRecyclingTip(id);
        return ResponseEntity.noContent().build();
    }
}
