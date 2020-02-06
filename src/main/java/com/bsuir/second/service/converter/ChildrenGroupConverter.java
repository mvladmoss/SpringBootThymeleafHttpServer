package com.bsuir.second.service.converter;

import com.bsuir.second.exception.ServiceException;
import com.bsuir.second.model.dto.ChildrenGroupDto;
import com.bsuir.second.model.entity.ChildrenGroup;
import com.bsuir.second.model.entity.Teacher;
import com.bsuir.second.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChildrenGroupConverter implements Converter<ChildrenGroup, ChildrenGroupDto> {

    private final TeacherConverter teacherConverter;
    private final TeacherRepository teacherRepository;

    @Override
    public ChildrenGroupDto convert(ChildrenGroup childrenGroup) {
        return ChildrenGroupDto.builder()
                .id(childrenGroup.getId())
                .ageGroup(childrenGroup.getAgeGroup())
                .groupNumber(childrenGroup.getGroupNumber())
                .groupName(childrenGroup.getGroupName())
                .teacher(childrenGroup.getTeacher() == null ? null :
                        teacherConverter.convert(childrenGroup.getTeacher()))
                .build();
    }

    @Override
    public ChildrenGroup unconvert(ChildrenGroupDto childrenGroupDto) {
        ChildrenGroup group = ChildrenGroup.builder()
                .id(childrenGroupDto.getId())
                .ageGroup(childrenGroupDto.getAgeGroup())
                .groupNumber(childrenGroupDto.getGroupNumber())
                .groupName(childrenGroupDto.getGroupName())
                .build();
        Teacher teacher = teacherRepository.findById(childrenGroupDto.getTeacher().getId())
                .orElseThrow(() -> new ServiceException(String.format("There is no teacher with id %d",
                        childrenGroupDto.getTeacher().getId())));
        group.setTeacher(teacher);
        return group;
    }
}
