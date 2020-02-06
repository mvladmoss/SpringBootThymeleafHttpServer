package com.bsuir.second.controller;

import java.util.Collections;
import java.util.List;

import com.bsuir.second.model.dto.SpecializationDto;
import com.bsuir.second.service.SpecializationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/specializations")
@RequiredArgsConstructor
public class SpecializationController {

    private final SpecializationService specializationService;

    @GetMapping("/home")
    public String specializationHome() {
        return "specialization_home";
    }

    @GetMapping("/all")
    public String findALl(Model model) {
        List<SpecializationDto> specializations = specializationService.findAll();
        model.addAttribute("specializations", specializations);
        return "specializations";
    }

    @GetMapping
    public String findByName(Model model, @RequestParam String name) {
        SpecializationDto specialization = specializationService.findByName(name);
        model.addAttribute("specializations", Collections.singletonList(specialization));
        return "specializations";
    }

    @PostMapping("/")
    public String saveSpecialization(Model model, @RequestParam String name, @RequestParam String description) {
        SpecializationDto specializationDto = SpecializationDto.builder()
                .name(name)
                .description(description)
                .build();
        specializationService.save(specializationDto);
        List<SpecializationDto> specializations = specializationService.findAll();
        model.addAttribute("specializations", specializations);
        return "specializations";
    }

    @PostMapping("/update")
    public String udpateSpecialization(Model model, @RequestParam Long id, @RequestParam String name, @RequestParam String description) {
        SpecializationDto specializationDto = SpecializationDto.builder()
                .id(id)
                .name(name)
                .description(description)
                .build();
        specializationService.update(specializationDto);
        List<SpecializationDto> specializations = specializationService.findAll();
        model.addAttribute("specializations", specializations);
        return "specializations";
    }

    @GetMapping("/save_page")
    public String getSavePage(Model model) {
        model.addAttribute("mode", "create");
        return "save_specializations";
    }

    @GetMapping("/update_page")
    public String getUpdatePage(Model model, @RequestParam Long id) {
        SpecializationDto specialization = specializationService.findById(id);
        model.addAttribute("mode", "update");
        model.addAttribute("specialization", specialization);
        return "save_specializations";
    }

    @GetMapping("/delete")
    public String deleteSpecialization(Model model, @RequestParam Long id) {
        specializationService.remove(id);
        List<SpecializationDto> specializations = specializationService.findAll();
        model.addAttribute("specializations", specializations);
        return "specializations";
    }
}
