package com.enviro.assessment.grad001.andilekhumalo.controller;

import com.enviro.assessment.grad001.andilekhumalo.model.DisposalGuidelines;
import com.enviro.assessment.grad001.andilekhumalo.service.DisposalGuidelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing disposal guidelines.
 */
@RestController
@RequestMapping("/api/disposal-guidelines")
public class DisposalGuidelineController {
    private final DisposalGuidelineService service;

    /**
     * Constructs a new DisposalGuidelineController with the specified service.
     *
     * @param service the service to be used for managing disposal guidelines
     */
    @Autowired
    public DisposalGuidelineController(DisposalGuidelineService service) {
        this.service = service;
    }

    /**
     * Retrieves all disposal guidelines.
     *
     * @return a ResponseEntity containing the list of all disposal guidelines and an HTTP status code
     */
    @GetMapping
    public ResponseEntity<List<DisposalGuidelines>> getAllGuidelines() {
        List<DisposalGuidelines> guidelines = service.getAllGuidelines();
        return ResponseEntity.status(HttpStatus.OK).body(guidelines);
    }

    /**
     * Retrieves a disposal guideline by its ID.
     *
     * @param id the ID of the disposal guideline to retrieve
     * @return a ResponseEntity containing the disposal guideline and an HTTP status code
     */
    @GetMapping("/{id}")
    public ResponseEntity<DisposalGuidelines> getGuidelineById(@PathVariable Long id) {
        DisposalGuidelines guideline = service.getGuidelineById(id);
        return ResponseEntity.status(HttpStatus.OK).body(guideline);
    }

    /**
     * Adds a new disposal guideline.
     *
     * @param disposalGuideline the disposal guideline to add
     * @return a ResponseEntity containing the newly added disposal guideline and an HTTP status code
     */
    @PostMapping
    public ResponseEntity<DisposalGuidelines> addGuideline(@Validated @RequestBody DisposalGuidelines disposalGuideline) {
        DisposalGuidelines newGuideline = service.addGuideline(disposalGuideline);
        return ResponseEntity.status(HttpStatus.CREATED).body(newGuideline);
    }

    /**
     * Updates an existing disposal guideline.
     *
     * @param id the ID of the disposal guideline to update
     * @param disposalGuideline the updated disposal guideline
     * @return a ResponseEntity containing the updated disposal guideline and an HTTP status code
     */
    @PutMapping("/{id}")
    public ResponseEntity<DisposalGuidelines> updateGuideline(@PathVariable Long id, @Validated @RequestBody DisposalGuidelines disposalGuideline) {
        DisposalGuidelines updateGuideline = service.updateGuideline(id, disposalGuideline);
        return ResponseEntity.status(HttpStatus.OK).body(updateGuideline);
    }

    /**
     * Deletes a disposal guideline by its ID.
     *
     * @param id the ID of the disposal guideline to delete
     * @return a ResponseEntity with no content and an HTTP status code
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGuideline(@PathVariable Long id) {
        service.deleteGuideline(id);
        return ResponseEntity.noContent().build();
    }
}