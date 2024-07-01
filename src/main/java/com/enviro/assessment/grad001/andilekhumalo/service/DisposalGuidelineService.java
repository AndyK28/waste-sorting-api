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

    public List<DisposalGuidelines> getAll() {
        List<DisposalGuidelines> guidelines = repository.findAll();
        if (guidelines.isEmpty()) {
            throw new NotFoundException("No Guidelines Found In The Database");
        }
        return guidelines;
    }

    public DisposalGuidelines getById(Long id) {
        Optional<DisposalGuidelines> disposalGuideline = repository.findById(id);
        if (disposalGuideline.isPresent()) {
            return disposalGuideline.get();
        }
        throw new NotFoundException("Disposal Guideline Not Found");
    }

    public DisposalGuidelines save(DisposalGuidelines disposalGuideline) {
        return repository.save(disposalGuideline);
    }

    public DisposalGuidelines update(Long id, DisposalGuidelines disposalGuideline) {
        DisposalGuidelines existingGuideline = getById(id);
        existingGuideline.setGuideline(disposalGuideline.getGuideline());
        return repository.save(existingGuideline);
    }

    public  void delete(Long id) {
        DisposalGuidelines disposalGuideline = getById(id);
        repository.delete(disposalGuideline);
    }
}
