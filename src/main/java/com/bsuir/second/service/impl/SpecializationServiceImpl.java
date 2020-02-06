package com.bsuir.second.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.bsuir.second.exception.ServiceException;
import com.bsuir.second.model.dto.SpecializationDto;
import com.bsuir.second.model.entity.Specialization;
import com.bsuir.second.repository.SpecializationRepository;
import com.bsuir.second.service.SpecializationService;
import com.bsuir.second.service.converter.SpecializationConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SpecializationServiceImpl implements SpecializationService {

    private final SpecializationRepository specializationRepository;
    private final SpecializationConverter specializationConverter;
    @Autowired
    private SpecializationService specializationService;

    @Override
    @Transactional
    public List<SpecializationDto> findAll() {
        return specializationRepository.findAll().stream()
                .map(specializationConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SpecializationDto findById(Long id) {
        return specializationRepository.findById(id)
                .map(specializationConverter::convert)
                .orElseThrow(() -> new ServiceException(String.format("There is no specialization with id %d", id)));
    }

    @Override
    @Transactional
    public SpecializationDto findByName(String name) {
        return specializationRepository.findByName(name)
                .map(specializationConverter::convert)
                .orElseThrow(() -> new IllegalArgumentException(String.format("There is no specializations with name %s", name)));
    }

    @Override
    @Transactional
    public SpecializationDto save(SpecializationDto specializationDto) {
        Optional<Specialization> maybeSpecialization = specializationRepository.findByName(specializationDto.getName());
        if (maybeSpecialization.isPresent()) {
            throw new ServiceException(String.format("Specialization with name %s has already existed", specializationDto.getName()));
        }
        Specialization specialization = specializationConverter.unconvert(specializationDto);
        Specialization spec = specializationRepository.save(specialization);
        return specializationService.findById(spec.getId());
    }

    @Override
    @Transactional
    public SpecializationDto update(SpecializationDto specializationDto) {
        Optional<Specialization> maybeSpecialization = specializationRepository.findByName(specializationDto.getName());
        if (maybeSpecialization.isPresent() && !maybeSpecialization.get().getId().equals(specializationDto.getId())) {
            throw new ServiceException(String.format("Specialization with name %s has already existed", specializationDto.getName()));
        }
        Specialization specialization = specializationRepository.getOne(specializationDto.getId());
        specialization.setName(specializationDto.getName());
        specialization.setDescription(specializationDto.getDescription());
        return specializationService.findById(specialization.getId());
    }

    @Override
    @Transactional
    public void remove(Long id) {
        specializationRepository.deleteById(id);
    }
}
