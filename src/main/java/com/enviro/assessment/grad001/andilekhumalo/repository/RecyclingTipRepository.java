package com.enviro.assessment.grad001.andilekhumalo.repository;

import com.enviro.assessment.grad001.andilekhumalo.model.RecyclingTips;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecyclingTipRepository extends JpaRepository<RecyclingTips, Long> {
}
