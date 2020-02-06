package com.bsuir.second.repository;

import java.util.Optional;

import com.bsuir.second.model.entity.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecializationRepository extends JpaRepository<Specialization, Long> {

    Optional<Specialization> findByName(String name);
}
