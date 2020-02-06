package com.bsuir.second.service;

import java.util.List;

import com.bsuir.second.model.dto.TeacherDto;

public interface TeacherService {

    TeacherDto findById(Long id);

    TeacherDto saveTeacher(TeacherDto childDto);

    TeacherDto updateTeacher(TeacherDto teacherDto);

    void remove(Long id);

    List<TeacherDto> findAll();

    TeacherDto findByNameAndSurname(String name, String surname);
}
