package com.bsuir.second.service.impl;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.bsuir.second.exception.ServiceException;
import com.bsuir.second.model.dto.ChildDto;
import com.bsuir.second.model.dto.ChildrenGroupData;
import com.bsuir.second.model.dto.ChildrenGroupDto;
import com.bsuir.second.model.entity.ChildrenGroup;
import com.bsuir.second.model.entity.Teacher;
import com.bsuir.second.repository.ChildRepository;
import com.bsuir.second.repository.ChildrenGroupRepository;
import com.bsuir.second.repository.TeacherRepository;
import com.bsuir.second.service.ChildrenGroupService;
import com.bsuir.second.service.converter.ChildConverter;
import com.bsuir.second.service.converter.ChildrenGroupConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChildrenGroupServiceImpl implements ChildrenGroupService {

    private final ChildrenGroupRepository childrenGroupRepository;
    private final ChildRepository childRepository;
    private final ChildConverter childConverter;
    private final ChildrenGroupConverter childrenGroupConverter;
    private final TeacherRepository teacherRepository;
    @Autowired
    private ChildrenGroupService childrenGroupService;

    public ChildrenGroupServiceImpl(ChildrenGroupRepository childrenGroupRepository, ChildRepository childRepository, ChildConverter childConverter, ChildrenGroupConverter childrenGroupConverter, TeacherRepository teacherRepository) {
        this.childrenGroupRepository = childrenGroupRepository;
        this.childRepository = childRepository;
        this.childConverter = childConverter;
        this.childrenGroupConverter = childrenGroupConverter;
        this.teacherRepository = teacherRepository;
    }

    @Override
    public ChildrenGroupData findChildrenGroupData(Integer groupNumber) {
        ChildrenGroup childrenGroup = childrenGroupRepository.findByGroupNumber(groupNumber)
                .orElseThrow(() -> new IllegalArgumentException(String.format("There is no children group with group " +
                                "number %d",
                        groupNumber)));
        List<ChildDto> children = childRepository.findByGroupId(childrenGroup.getId()).stream()
                .map(childConverter::convert)
                .collect(Collectors.toList());
        return ChildrenGroupData.builder()
                .children(children)
                .currentData(Instant.now())
                .build();
    }

    @Override
    public ChildrenGroupDto save(ChildrenGroupDto childrenGroupDto) {
        Optional<ChildrenGroup> maybeGroup =
                childrenGroupRepository.findByGroupNumber(childrenGroupDto.getGroupNumber());
        if (maybeGroup.isPresent()) {
            throw new ServiceException(String.format("Group with number %d has already exist",
                    childrenGroupDto.getGroupNumber()));
        }
        ChildrenGroup group = childrenGroupConverter.unconvert(childrenGroupDto);
        childrenGroupRepository.save(group);
        return childrenGroupService.findByGroupNumber(childrenGroupDto.getGroupNumber());
    }

    @Override
    public ChildrenGroupDto update(ChildrenGroupDto childrenGroupDto) {
        Optional<ChildrenGroup> maybeChildrenGroup =
                childrenGroupRepository.findByGroupNumber(childrenGroupDto.getGroupNumber());
        if (maybeChildrenGroup.isPresent() && !maybeChildrenGroup.get().getId().equals(childrenGroupDto.getId())) {
            throw new ServiceException(String.format("Group with number %d has already exist", childrenGroupDto.getGroupNumber()));
        }
        ChildrenGroup group = childrenGroupRepository.getOne(childrenGroupDto.getId());
        group.setGroupName(childrenGroupDto.getGroupName());
        group.setAgeGroup(childrenGroupDto.getAgeGroup());
        Teacher teacher = teacherRepository.getOne(childrenGroupDto.getTeacher().getId());
        group.setTeacher(teacher);
        return childrenGroupService.findDataById(childrenGroupDto.getId());
    }

    @Override
    public List<ChildrenGroupDto> findAll() {
        return childrenGroupRepository.findAll().stream()
                .map(childrenGroupConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public ChildrenGroupDto findByGroupNumber(Integer id) {
        return childrenGroupRepository.findByGroupNumber(id)
                .map(childrenGroupConverter::convert)
                .orElseThrow(() -> new ServiceException(String.format("There is no children group with id %d", id)));
    }

    @Override
    public ChildrenGroupDto findDataById(Long id) {
        ChildrenGroup childrenGroup = childrenGroupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("There is no group with id %d", id)));
        return childrenGroupConverter.convert(childrenGroup);
    }

    @Override
    public void remove(Long id) {
        childrenGroupRepository.deleteById(id);
    }
}
