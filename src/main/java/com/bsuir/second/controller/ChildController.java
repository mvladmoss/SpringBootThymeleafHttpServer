package com.bsuir.second.controller;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.bsuir.second.model.dto.ChildDataDto;
import com.bsuir.second.model.dto.ChildDto;
import com.bsuir.second.model.dto.ChildrenGroupDto;
import com.bsuir.second.service.ChildrenGroupService;
import com.bsuir.second.service.ChildrenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/children")
@RequiredArgsConstructor
public class ChildController {

    private final ChildrenService childrenService;
    private final ChildrenGroupService childrenGroupService;

    @GetMapping("/home")
    public String childHome(Model model) {
        return "child_home";
    }

    @GetMapping("/all")
    public String findAll(Model model) {
        List<ChildDataDto> children = childrenService.findAll();
        model.addAttribute("children", children);
        return "children";
    }

    @GetMapping("/")
    public String findChild(Model model, @RequestParam String name, @RequestParam String surname) {
        ChildDataDto childDto = childrenService.findByNameAndSurname(name, surname);
        model.addAttribute("children", Collections.singletonList(childDto));
        return "children";
    }

    @PostMapping("/")
    public String saveChild(Model model, @RequestParam String name, @RequestParam String surname, @RequestParam Integer age,
                            @RequestParam String phone, @RequestParam Integer groupNumber) {
        ChildrenGroupDto childrenGroupDto = childrenGroupService.findByGroupNumber(groupNumber);
        ChildDto childDto = ChildDto.builder()
                .name(name)
                .surname(surname)
                .age(age)
                .phone(phone)
                .groupNumber(childrenGroupDto.getGroupNumber())
                .build();
        childrenService.saveChild(childDto);
        List<ChildDataDto> children = childrenService.findAll();
        model.addAttribute("children", children);
        return "children";
    }

    @PostMapping("/update")
    public String updateChild(Model model, @RequestParam Long id, @RequestParam String name, @RequestParam String surname, @RequestParam Integer age,
                            @RequestParam String phone, @RequestParam Integer groupNumber) {
        ChildrenGroupDto childrenGroupDto = childrenGroupService.findByGroupNumber(groupNumber);
        ChildDto childDto = ChildDto.builder()
                .id(id)
                .name(name)
                .surname(surname)
                .age(age)
                .phone(phone)
                .groupNumber(childrenGroupDto.getGroupNumber())
                .build();
        childrenService.updateChild(childDto);
        List<ChildDataDto> children = childrenService.findAll();
        model.addAttribute("children", children);
        return "children";
    }

    @GetMapping("/update_page")
    public String updateChild(Model model, @RequestParam Long id) {
        ChildDataDto child = childrenService.findChildDataDto(id);
        model.addAttribute("_child_", child);
        model.addAttribute("mode", "update");
        String availableGroups = childrenGroupService.findAll().stream()
                .map(ChildrenGroupDto::getGroupNumber)
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        model.addAttribute("groups", availableGroups);
        return "save_child";
    }

    @GetMapping("/save_page")
    public String getCreatePage(Model model) {
        String availableGroups = childrenGroupService.findAll().stream()
                .map(ChildrenGroupDto::getGroupNumber)
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        model.addAttribute("groups", availableGroups);
        model.addAttribute("mode", "create");
        return "save_child";
    }

    @GetMapping("/delete")
    public String deleteChild(Model model, @RequestParam Long id) {
        ChildDto child = childrenService.findById(id);
        childrenService.remove(child.getId());
        List<ChildDataDto> children = childrenService.findAll();
        model.addAttribute("children", children);
        return "children";
    }

}
