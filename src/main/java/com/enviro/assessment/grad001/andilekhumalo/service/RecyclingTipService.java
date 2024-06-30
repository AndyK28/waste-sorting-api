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
    @Autowired
    private RecyclingTipRepository repository;

    public List<RecyclingTips> getAllTips() {
        return repository.findAll();
    }

    public RecyclingTips getTipById(Long id) {
        Optional<RecyclingTips> tip = repository.findById(id);
        if (tip.isPresent()) {
            return tip.get();
        }
        throw new NotFoundException("Tip Not Found");
    }

    public RecyclingTips saveTip(RecyclingTips tip) {
        return repository.save(tip);
    }

    public RecyclingTips updateTip(Long id, RecyclingTips tip) {
        RecyclingTips existingTip = getTipById(id);
        existingTip.setTip(tip.getTip());
        return repository.save(existingTip);
    }

    public void deleteTip(Long id) {
        RecyclingTips tip = getTipById(id);
        repository.delete(tip);
    }
}
