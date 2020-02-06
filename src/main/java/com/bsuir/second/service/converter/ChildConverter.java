package com.bsuir.second.service.converter;

import com.bsuir.second.exception.ServiceException;
import com.bsuir.second.model.dto.ChildDto;
import com.bsuir.second.model.entity.Child;
import com.bsuir.second.model.entity.ChildrenGroup;
import com.bsuir.second.repository.ChildrenGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChildConverter implements Converter<Child, ChildDto> {

    private final ChildrenGroupRepository childrenGroupRepository;

    @Override
    public ChildDto convert(Child child) {
        return ChildDto.builder()
                .id(child.getId())
                .name(child.getName())
                .surname(child.getSurname())
                .age(child.getAge())
                .phone(child.getPhone())
                .groupNumber(child.getChildrenGroup().getGroupNumber())
                .build();
    }

    @Override
    public Child unconvert(ChildDto childDto) {
        Child child = Child.builder()
                .id(childDto.getId())
                .name(childDto.getName())
                .surname(childDto.getSurname())
                .age(childDto.getAge())
                .phone(childDto.getPhone())
                .build();
        ChildrenGroup childrenGroup = childrenGroupRepository.findByGroupNumber(childDto.getGroupNumber())
                .orElseThrow(() -> new ServiceException(String.format("There is no children group with number %d",
                        childDto.getGroupNumber())));
        child.setChildrenGroup(childrenGroup);
        return child;
    }
}
