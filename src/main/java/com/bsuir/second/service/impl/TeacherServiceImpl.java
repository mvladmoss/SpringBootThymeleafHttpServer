package com.bsuir.second.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.bsuir.second.exception.ServiceException;
import com.bsuir.second.model.dto.TeacherDto;
import com.bsuir.second.model.entity.Teacher;
import com.bsuir.second.repository.SpecializationRepository;
import com.bsuir.second.repository.TeacherRepository;
import com.bsuir.second.service.TeacherService;
import com.bsuir.second.service.converter.TeacherConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherConverter teacherConverter;
    private final TeacherRepository teacherRepository;
    private final SpecializationRepository specializationRepository;
    @Autowired
    private TeacherService teacherService;

    @Override
    @Transactional(readOnly = true)
    public TeacherDto findById(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ServiceException(String.format("There is no teacher with id %s", id)));
        return teacherConverter.convert(teacher);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeacherDto> findAll() {
        List<Teacher> teacherPage = teacherRepository.findAll();
        return teacherPage.stream()
                .map(teacherConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public TeacherDto findByNameAndSurname(String name, String surname) {
        return teacherRepository.findByNameAndSurname(name, surname)
                .map(teacherConverter::convert)
                .orElseThrow(() -> new ServiceException(String.format("There is no teacher %s",
                        name + surname)));
    }

    @Override
    @Transactional
    public TeacherDto saveTeacher(TeacherDto teacherDto) {
        Optional<Teacher> maybeTeacher = teacherRepository.findByNameAndSurname(teacherDto.getName(),
                teacherDto.getSurname());
        if (maybeTeacher.isPresent()) {
            throw new ServiceException(String.format("Teacher %s %s has already exist", teacherDto.getName(),
                    teacherDto.getSurname()));
        }
        validatePhone(null, teacherDto.getPhone());
        Teacher teacher = teacherConverter.unconvert(teacherDto);
        teacherRepository.save(teacher);
        return teacherService.findById(teacher.getId());
    }

    @Transactional
    public TeacherDto updateTeacher(TeacherDto teacherDto) {
        Optional<Teacher> maybeTeacher = teacherRepository.findByNameAndSurname(teacherDto.getName(),
                teacherDto.getSurname());
        if (maybeTeacher.isPresent() && !maybeTeacher.get().getId().equals(teacherDto.getId())) {
            throw new ServiceException(String.format("Teacher with name %s and surname %s has already exist",
                    teacherDto.getName(), teacherDto.getSurname()));
        }
        Teacher teacher = teacherRepository.getOne(teacherDto.getId());
        validatePhone(teacher.getId(), teacher.getPhone());
        teacher.setBirthdayDate(teacherDto.getBirthdayDate());
        teacher.setGender(teacherDto.getGender());
        teacher.setName(teacherDto.getName());
        teacher.setSurname(teacherDto.getSurname());
        teacher.setPhone(teacherDto.getPhone());
        teacher.setSpecializations(teacherDto.getSpecializations().stream()
                .map(spec -> specializationRepository.findByName(spec.getName())
                        .orElseThrow(() -> new ServiceException(String.format("There is no specializations " +
                                "with name %s", spec.getName()))))
                .collect(Collectors.toSet()));
        teacherRepository.save(teacher);
        return this.findById(teacher.getId());
    }

    @Override
    @Transactional
    public void remove(Long id) {
        teacherRepository.deleteById(id);
    }

    private void validatePhone(Long teacherId, String phone) {
        Optional<Teacher> maybeTeacher = teacherRepository.findByPhone(phone);
        if (maybeTeacher.isPresent() && !maybeTeacher.get().getId().equals(teacherId)) {
            throw new ServiceException(String.format("Teacher with phone %s has already exists", phone));
        }
    }
}
