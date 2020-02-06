package com.bsuir.second.service.converter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.bsuir.second.model.dto.SpecializationDto;
import com.bsuir.second.model.dto.TeacherDto;
import com.bsuir.second.model.entity.Specialization;
import com.bsuir.second.model.entity.Teacher;
import com.bsuir.second.repository.SpecializationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TeacherConverter implements Converter<Teacher, TeacherDto> {

    private final SpecializationConverter specializationConverter;
    private final SpecializationRepository specializationRepository;

    @Override
    public TeacherDto convert(Teacher teacher) {
        return TeacherDto.builder()
                .id(teacher.getId())
                .birthdayDate(teacher.getBirthdayDate())
                .gender(teacher.getGender())
                .name(teacher.getName())
                .surname(teacher.getSurname())
                .phone(teacher.getPhone())
                .specializations(teacher.getSpecializations().stream()
                        .map(specializationConverter::convert)
                        .collect(Collectors.toSet()))
                .build();
    }

    @Override
    public Teacher unconvert(TeacherDto teacherDto) {
        Teacher teacher = Teacher.builder()
                .id(teacherDto.getId())
                .birthdayDate(teacherDto.getBirthdayDate())
                .gender(teacherDto.getGender())
                .name(teacherDto.getName())
                .surname(teacherDto.getSurname())
                .phone(teacherDto.getPhone())
                .build();
        Set<SpecializationDto> dtoSpecializations = teacherDto.getSpecializations();
        Set<Specialization> specializations = new HashSet<>();
        dtoSpecializations.forEach(specializationDto -> specializations.add(specializationRepository
                .findByName(specializationDto.getName())
                .orElse(specializationConverter.unconvert(specializationDto))));
        teacher.setSpecializations(specializations);
        return teacher;
    }
}
