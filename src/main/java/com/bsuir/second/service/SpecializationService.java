package com.bsuir.second.service;

import java.util.List;

import com.bsuir.second.model.dto.SpecializationDto;

public interface SpecializationService {

    List<SpecializationDto> findAll();

    SpecializationDto findById(Long id);

    SpecializationDto findByName(String name);

    SpecializationDto save(SpecializationDto specializationDto);

    SpecializationDto update(SpecializationDto specializationDto);

    void remove(Long id);
}
