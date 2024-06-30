package com.enviro.assessment.grad001.andilekhumalo.service;

import com.enviro.assessment.grad001.andilekhumalo.exception.NotFoundException;
import com.enviro.assessment.grad001.andilekhumalo.model.DisposalGuideline;
import com.enviro.assessment.grad001.andilekhumalo.repository.DisposalGuidelineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DisposalGuidelineService {
    @Autowired
    private DisposalGuidelineRepository repository;

    public List<DisposalGuideline> getAll() {
        return repository.findAll();
    }

    public DisposalGuideline getById(Long id) {
        Optional<DisposalGuideline> disposalGuideline = repository.findById(id);
        if (disposalGuideline.isPresent()) {
            return disposalGuideline.get();
        }
        throw new NotFoundException("Disposal Guideline Not Found");
    }

    public DisposalGuideline save(DisposalGuideline disposalGuideline) {
        return repository.save(disposalGuideline);
    }

    public DisposalGuideline update(Long id, DisposalGuideline disposalGuideline) {
        DisposalGuideline existingGuideline = getById(id);
        existingGuideline.setGuideline(disposalGuideline.getGuideline());
        return repository.save(existingGuideline);
    }

    public  void delete(Long id) {
        DisposalGuideline disposalGuideline = getById(id);
        repository.delete(disposalGuideline);
    }
}
