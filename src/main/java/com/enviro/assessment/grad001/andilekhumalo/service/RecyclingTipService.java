package com.enviro.assessment.grad001.andilekhumalo.service;

import com.enviro.assessment.grad001.andilekhumalo.exception.NotFoundException;
import com.enviro.assessment.grad001.andilekhumalo.model.RecyclingTIps;
import com.enviro.assessment.grad001.andilekhumalo.repository.RecyclingTipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecyclingTipService {
    @Autowired
    private RecyclingTipRepository repository;

    public List<RecyclingTIps> getAllTips() {
        return repository.findAll();
    }

    public RecyclingTIps getTipById(Long id) {
        Optional<RecyclingTIps> tip = repository.findById(id);
        if (tip.isPresent()) {
            return tip.get();
        }
        throw new NotFoundException("Tip Not Found");
    }

    public RecyclingTIps saveTip(RecyclingTIps tip) {
        return repository.save(tip);
    }

    public RecyclingTIps updateTip(Long id, RecyclingTIps tip) {
        RecyclingTIps existingTip = getTipById(id);
        existingTip.setTip(tip.getTip());
        return repository.save(existingTip);
    }

    public void deleteTip(Long id) {
        RecyclingTIps tip = getTipById(id);
        repository.delete(tip);
    }
}
