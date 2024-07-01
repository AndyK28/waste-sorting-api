package com.enviro.assessment.grad001.andilekhumalo.service;

import com.enviro.assessment.grad001.andilekhumalo.exception.NotFoundException;
import com.enviro.assessment.grad001.andilekhumalo.model.DisposalGuidelines;
import com.enviro.assessment.grad001.andilekhumalo.repository.DisposalGuidelineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DisposalGuidelineService {
    @Autowired
    private DisposalGuidelineRepository repository;

    public List<DisposalGuidelines> getAllGuidelines() {
        List<DisposalGuidelines> guidelines = repository.findAll();
        if (guidelines.isEmpty()) {
            throw new NotFoundException("Error 404: Not Found");
        }
        return guidelines;
    }

    public DisposalGuidelines getGuidelineById(Long id) {
        Optional<DisposalGuidelines> disposalGuideline = repository.findById(id);
        if (disposalGuideline.isPresent()) {
            return disposalGuideline.get();
        }
        throw new NotFoundException("Error 404: Not Found");
    }

    public DisposalGuidelines addGuideline(DisposalGuidelines disposalGuideline) {
        return repository.save(disposalGuideline);
    }

    public DisposalGuidelines updateGuideline(Long id, DisposalGuidelines disposalGuideline) {
        DisposalGuidelines existingGuideline = getGuidelineById(id);
        existingGuideline.setGuideline(disposalGuideline.getGuideline());
        return repository.save(existingGuideline);
    }

    public void deleteGuideline(Long id) {
        DisposalGuidelines disposalGuideline = getGuidelineById(id);
        repository.delete(disposalGuideline);
    }
}