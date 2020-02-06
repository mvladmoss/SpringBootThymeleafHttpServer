package com.bsuir.second.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.bsuir.second.model.GenderEnum;
import com.bsuir.second.model.dto.SpecializationDto;
import com.bsuir.second.model.dto.TeacherDto;
import com.bsuir.second.service.SpecializationService;
import com.bsuir.second.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;
    private final SpecializationService specializationService;

    @GetMapping("/home")
    public String teacherHome() {
        return "teacher_home";
    }

    @GetMapping("/")
    public String findTeacher(Model model, @RequestParam("name") String name, @RequestParam("surname") String surname) {
        TeacherDto teacher = teacherService.findByNameAndSurname(name, surname);
        model.addAttribute("teachers", Collections.singletonList(teacher));
        return "teachers";
    }

    @GetMapping("/all")
    public String findAll(Model model) {
        List<TeacherDto> teachers = teacherService.findAll();
        model.addAttribute("teachers", teachers);
        return "teachers";
    }

    @PostMapping("/")
    public String createTeacher(Model model, @RequestParam String name, @RequestParam String surname,
                                @RequestParam String birthday,
                                @RequestParam String gender, @RequestParam String specializations,
                                @RequestParam String phone) {
        TeacherDto teacherDto = TeacherDto.builder()
                .name(name)
                .surname(surname)
                .gender(GenderEnum.valueOf(gender.toUpperCase()))
                .birthdayDate(LocalDate.parse(birthday))
                .phone(phone)
                .specializations(Arrays.stream(specializations.split(","))
                        .map(specializationService::findByName)
                        .collect(Collectors.toSet()))
                .build();
        teacherService.saveTeacher(teacherDto);
        String teacherSpecializations = getSpecializations(teacherDto);
        model.addAttribute("teachers_specializations", teacherSpecializations);
        List<TeacherDto> teachers = teacherService.findAll();
        model.addAttribute("teachers", teachers);
        return "teachers";
    }

    @GetMapping("/save_page")
    public String getCreatePage(Model model) {
        String specializations = specializationService.findAll().stream()
                .map(SpecializationDto::getName)
                .collect(Collectors.joining(","));
        model.addAttribute("specializations", specializations);
        model.addAttribute("mode", "create");
        return "save_teacher";
    }

    @GetMapping("/update_page")
    public String getCreatePage(Model model, @RequestParam Long id) {
        TeacherDto teacherDto = teacherService.findById(id);
        String specializations = specializationService.findAll().stream()
                .map(SpecializationDto::getName)
                .collect(Collectors.joining(","));
        String teacherSpecializations = getSpecializations(teacherDto);
        model.addAttribute("teacher", teacherDto);
        model.addAttribute("teacher_specializations", teacherSpecializations);
        model.addAttribute("specializations", specializations);
        model.addAttribute("mode", "update");
        return "save_teacher";
    }

    @PostMapping("/update")
    public String updateTeacher(Model model, @RequestParam Long id, @RequestParam String name, @RequestParam String surname,
                                @RequestParam String birthday, @RequestParam String gender,
                                @RequestParam String specializations,
                                @RequestParam String phone) {
        TeacherDto teacherDto = TeacherDto.builder()
                .id(id)
                .name(name)
                .surname(surname)
                .gender(GenderEnum.valueOf(gender.toUpperCase()))
                .birthdayDate(LocalDate.parse(birthday))
                .phone(phone)
                .specializations(Arrays.stream(specializations.split(","))
                        .map(specializationService::findByName)
                        .collect(Collectors.toSet()))
                .build();
        teacherService.updateTeacher(teacherDto);
        List<TeacherDto> teachers = teacherService.findAll();
        model.addAttribute("teachers", teachers);
        return "teachers";
    }

    @GetMapping("/delete")
    public String deleteTeacher(Model model, @RequestParam String name, @RequestParam String surname) {
        TeacherDto teacherDto = teacherService.findByNameAndSurname(name, surname);
        teacherService.remove(teacherDto.getId());
        List<TeacherDto> teachers = teacherService.findAll();
        model.addAttribute("teachers", teachers);
        return "teachers";
    }

    private String getSpecializations(TeacherDto teacherDto) {
        return teacherDto.getSpecializations().stream()
                .map(SpecializationDto::getName)
                .collect(Collectors.joining(","));
    }
}
