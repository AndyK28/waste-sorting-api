package com.enviro.assessment.grad001.andilekhumalo.controller;

import com.enviro.assessment.grad001.andilekhumalo.model.DisposalGuidelines;
import com.enviro.assessment.grad001.andilekhumalo.service.DisposalGuidelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disposal-guidelines")
public class DisposalGuidelineController {
    @Autowired
    private DisposalGuidelineService service;

    @GetMapping
    public List<DisposalGuidelines> getAllGuidelines() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public DisposalGuidelines getGuidelineById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public DisposalGuidelines addGuideline(@Validated @RequestBody DisposalGuidelines disposalGuideline) {
        return service.save(disposalGuideline);
    }

    @PutMapping("/{id}")
    public DisposalGuidelines updateGuideline(@PathVariable Long id, @Validated @RequestBody DisposalGuidelines disposalGuideline) {
        return service.update(id, disposalGuideline);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGuideline(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
