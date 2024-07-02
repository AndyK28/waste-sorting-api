package com.enviro.assessment.grad001.andilekhumalo.controller;

import com.enviro.assessment.grad001.andilekhumalo.model.RecyclingTips;
import com.enviro.assessment.grad001.andilekhumalo.service.RecyclingTipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing recycling tips.
 */
@RestController
@RequestMapping("/api/recycling-tips")
public class RecyclingTipController {
    private final RecyclingTipService service;

    /**
     * Constructs a new RecyclingTipController with the specified service.
     *
     * @param service the service to be used for managing recycling tips
     */
    @Autowired
    public RecyclingTipController(RecyclingTipService service) {
        this.service = service;
    }

    /**
     * Retrieves all recycling tips.
     *
     * @return a ResponseEntity containing the list of all recycling tips and an HTTP status code
     */
    @GetMapping
    public ResponseEntity<List<RecyclingTips>> getAllRecyclingTips() {
        List<RecyclingTips> tips = service.getAllRecyclingTips();
        return ResponseEntity.status(HttpStatus.OK).body(tips);
    }

    /**
     * Retrieves a recycling tip by its ID.
     *
     * @param id the ID of the recycling tip to retrieve
     * @return a ResponseEntity containing the recycling tip and an HTTP status code
     */
    @GetMapping("/{id}")
    public ResponseEntity<RecyclingTips> getRecyclingTipById(@PathVariable Long id) {
        RecyclingTips tip = service.getRecyclingTipById(id);
        return ResponseEntity.status(HttpStatus.OK).body(tip);
    }

    /**
     * Adds a new recycling tip.
     *
     * @param recyclingTips the recycling tip to add
     * @return a ResponseEntity containing the newly added recycling tip and an HTTP status code
     */
    @PostMapping
    public ResponseEntity<RecyclingTips> addRecyclingTips(@Validated @RequestBody RecyclingTips recyclingTips) {
        RecyclingTips tip = service.addRecyclingTip(recyclingTips);
        return ResponseEntity.status(HttpStatus.CREATED).body(tip);
    }

    /**
     * Updates an existing recycling tip.
     *
     * @param id the ID of the recycling tip to update
     * @param recyclingTips the updated recycling tip
     * @return a ResponseEntity containing the updated recycling tip and an HTTP status code
     */
    @PutMapping("/{id}")
    public ResponseEntity<RecyclingTips> updateRecyclingTips(@PathVariable Long id, @Validated @RequestBody RecyclingTips recyclingTips) {
        RecyclingTips tips = service.updateRecyclingTip(id, recyclingTips);
        return ResponseEntity.status(HttpStatus.OK).body(tips);
    }

    /**
     * Deletes a recycling tip by its ID.
     *
     * @param id the ID of the recycling tip to delete
     * @return a ResponseEntity with no content and an HTTP status code
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecyclingTips(@PathVariable Long id) {
        service.deleteRecyclingTip(id);
        return ResponseEntity.noContent().build();
    }
}
