package com.enviro.assessment.grad001.andilekhumalo.service;

import com.enviro.assessment.grad001.andilekhumalo.exception.NotFoundException;
import com.enviro.assessment.grad001.andilekhumalo.model.RecyclingTips;
import com.enviro.assessment.grad001.andilekhumalo.repository.RecyclingTipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecyclingTipService {
    private final RecyclingTipRepository repository;

    @Autowired
    public RecyclingTipService(RecyclingTipRepository repository) {
        this.repository = repository;
    }

    public List<RecyclingTips> getAllRecyclingTips() {
        List<RecyclingTips> recyclingTips = repository.findAll();
        if (recyclingTips.isEmpty()) {
            throw new NotFoundException("Error 404: Not Found");
        }
        return recyclingTips;
    }

    public RecyclingTips getRecyclingTipById(Long id) {
        Optional<RecyclingTips> tip = repository.findById(id);
        if (tip.isPresent()) {
            return tip.get();
        }
        throw new NotFoundException("Error 404: Not Found");
    }

    public RecyclingTips addRecyclingTip(RecyclingTips tip) {
        return repository.save(tip);
    }

    public RecyclingTips updateRecyclingTip(Long id, RecyclingTips tip) {
        RecyclingTips existingTip = getRecyclingTipById(id);
        existingTip.setTip(tip.getTip());
        return repository.save(existingTip);
    }

    public void deleteRecyclingTip(Long id) {
        RecyclingTips tip = getRecyclingTipById(id);
        repository.delete(tip);
    }
}
