package com.enviro.assessment.grad001.andilekhumalo.controller;

import com.enviro.assessment.grad001.andilekhumalo.model.DisposalGuidelines;
import com.enviro.assessment.grad001.andilekhumalo.service.DisposalGuidelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<DisposalGuidelines>> getAllGuidelines() {
        List<DisposalGuidelines> guidelines = service.getAllGuidelines();
        return ResponseEntity.status(HttpStatus.OK).body(guidelines);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisposalGuidelines> getGuidelineById(@PathVariable Long id) {
        DisposalGuidelines guideline = service.getGuidelineById(id);
        return ResponseEntity.status(HttpStatus.OK).body(guideline);
    }

    @PostMapping
    public ResponseEntity<DisposalGuidelines> addGuideline(@Validated @RequestBody DisposalGuidelines disposalGuideline) {
        DisposalGuidelines newGuideline = service.addGuideline(disposalGuideline);
        return ResponseEntity.status(HttpStatus.CREATED).body(newGuideline);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DisposalGuidelines> updateGuideline(@PathVariable Long id, @Validated @RequestBody DisposalGuidelines disposalGuideline) {
        DisposalGuidelines updateGuideline = service.updateGuideline(id, disposalGuideline);
        return ResponseEntity.status(HttpStatus.OK).body(updateGuideline);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGuideline(@PathVariable Long id) {
        service.deleteGuideline(id);
        return ResponseEntity.noContent().build();
    }
}
