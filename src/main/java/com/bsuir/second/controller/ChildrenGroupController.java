package com.bsuir.second.controller;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.bsuir.second.model.dto.ChildrenGroupDto;
import com.bsuir.second.model.dto.TeacherDto;
import com.bsuir.second.service.ChildrenGroupService;
import com.bsuir.second.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/children_groups")
@RequiredArgsConstructor
public class ChildrenGroupController {

    private final ChildrenGroupService childrenGroupService;
    private final TeacherService teacherService;

    @GetMapping("/home")
    public String childrenGroupHome() {
        return "children_group_home";
    }

    @GetMapping("/")
    public String findChildrenGroupByGroupNumber(Model model, @RequestParam Integer groupNumber) {
        ChildrenGroupDto group = childrenGroupService.findByGroupNumber(groupNumber);
        model.addAttribute("groups", Collections.singletonList(group));
        return "children_groups";
    }

    @GetMapping("/save_page")
    public String getCreatePage(Model model) {
        String availableTeachers = teacherService.findAll().stream()
                .map(teacher -> teacher.getName() + teacher.getSurname() + "(id=" + teacher.getId() + ")")
                .collect(Collectors.joining(","));
        model.addAttribute("teachers", availableTeachers);
        model.addAttribute("mode", "create");
        return "save_children_group";
    }

    @GetMapping("/update_page")
    public String getUpdatePage(Model model, @RequestParam Integer groupNumber) {
        String availableTeachers = teacherService.findAll().stream()
                .map(teacher -> teacher.getName() + teacher.getSurname() + "(id=" + teacher.getId() + ")")
                .collect(Collectors.joining(","));
        ChildrenGroupDto group = childrenGroupService.findByGroupNumber(groupNumber);
        model.addAttribute("group", group);
        model.addAttribute("teachers", availableTeachers);
        model.addAttribute("mode", "update");
        return "save_children_group";
    }

    @PostMapping("/")
    public String saveGroup(Model model, @RequestParam Integer groupNumber, @RequestParam String groupName,
                            @RequestParam Integer ageGroup,
                            @RequestParam Long teacherId) {
        TeacherDto teacher = teacherService.findById(teacherId);
        ChildrenGroupDto group = ChildrenGroupDto.builder()
                .groupName(groupName)
                .groupNumber(groupNumber)
                .ageGroup(ageGroup)
                .teacher(teacher)
                .build();
        childrenGroupService.save(group);
        List<ChildrenGroupDto> groups = childrenGroupService.findAll();
        model.addAttribute("groups", groups);
        return "children_groups";
    }

    @PostMapping("/update")
    public String updateGroup(Model model, @RequestParam Long id, @RequestParam Integer groupNumber,
                              @RequestParam String groupName, @RequestParam Integer ageGroup,
                              @RequestParam Long teacherId) {
        TeacherDto teacher = teacherService.findById(teacherId);
        ChildrenGroupDto childrenGroupDto = ChildrenGroupDto.builder()
                .id(id)
                .groupNumber(groupNumber)
                .groupName(groupName)
                .ageGroup(ageGroup)
                .teacher(teacher)
                .build();
        childrenGroupService.update(childrenGroupDto);
        List<ChildrenGroupDto> groups = childrenGroupService.findAll();
        model.addAttribute("groups", groups);
        return "children_groups";
    }

    @GetMapping("/all")
    public String findAll(Model model) {
        List<ChildrenGroupDto> groups = childrenGroupService.findAll();
        model.addAttribute("groups", groups);
        return "children_groups";
    }

    @GetMapping("/delete")
    public String deleteGroup(Model model, @RequestParam Long id) {
        ChildrenGroupDto group = childrenGroupService.findDataById(id);
        childrenGroupService.remove(group.getId());
        List<ChildrenGroupDto> groups = childrenGroupService.findAll();
        model.addAttribute("groups", groups);
        return "children_groups";
    }
}


