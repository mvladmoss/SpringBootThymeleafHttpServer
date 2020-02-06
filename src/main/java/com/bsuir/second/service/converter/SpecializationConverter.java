package com.bsuir.second.service.converter;

import com.bsuir.second.model.dto.SpecializationDto;
import com.bsuir.second.model.entity.Specialization;
import org.springframework.stereotype.Component;

@Component
public class SpecializationConverter implements Converter<Specialization, SpecializationDto> {

    @Override
    public SpecializationDto convert(Specialization specialization) {
        return SpecializationDto.builder()
                .id(specialization.getId())
                .name(specialization.getName())
                .description(specialization.getDescription())
                .build();
    }

    @Override
    public Specialization unconvert(SpecializationDto specializationDto) {
        return Specialization.builder()
                .id(specializationDto.getId())
                .name(specializationDto.getName())
                .description(specializationDto.getDescription())
                .build();
    }
}
