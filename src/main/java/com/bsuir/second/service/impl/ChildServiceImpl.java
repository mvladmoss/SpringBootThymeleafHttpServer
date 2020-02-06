package com.bsuir.second.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.bsuir.second.exception.ServiceException;
import com.bsuir.second.model.dto.ChildDataDto;
import com.bsuir.second.model.dto.ChildDto;
import com.bsuir.second.model.dto.ChildrenGroupDto;
import com.bsuir.second.model.entity.Child;
import com.bsuir.second.model.entity.ChildrenGroup;
import com.bsuir.second.repository.ChildRepository;
import com.bsuir.second.repository.ChildrenGroupRepository;
import com.bsuir.second.service.ChildrenGroupService;
import com.bsuir.second.service.ChildrenService;
import com.bsuir.second.service.converter.ChildConverter;
import com.bsuir.second.service.converter.ChildrenGroupConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ChildServiceImpl implements ChildrenService {

    private final ChildRepository childRepository;
    private final ChildrenGroupRepository childrenGroupRepository;
    private final ChildConverter childConverter;
    private final ChildrenGroupConverter childrenGroupConverter;
    private final ChildrenGroupService childrenGroupService;
    @Autowired
    private ChildrenService childrenService;

    @Override
    @Transactional
    public ChildDto findById(Long id) {
        Child child = childRepository.findById(id)
                .orElseThrow(() -> new ServiceException(String.format("There is no child with %s", id)));
        return childConverter.convert(child);
    }

    @Override
    public ChildDataDto findByNameAndSurname(String name, String surname) {
        ChildDto childDto = childRepository.findByNameAndSurname(name, surname)
                .map(childConverter::convert)
                .orElseThrow(() -> new ServiceException(String.format("There is no child %s %s", name, surname)));
        ChildrenGroupDto childrenGroup = childrenGroupService.findByGroupNumber(childDto.getGroupNumber());
        return ChildDataDto.builder()
                .child(childDto)
                .childrenGroup(childrenGroup)
                .build();
    }

    @Override
    @Transactional
    public List<ChildDataDto> findAll() {
        return childRepository.findAll().stream()
                .map(child -> ChildDataDto.builder()
                        .child(childConverter.convert(child))
                        .childrenGroup(childrenGroupService.findByGroupNumber(child.getChildrenGroup().getGroupNumber()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ChildDto saveChild(ChildDto childDto) {
        Optional<Child> maybeChild = childRepository.findByNameAndSurname(childDto.getName(), childDto.getSurname());
        if (maybeChild.isPresent()) {
            throw new ServiceException(String.format("Child with name %s and surname %s has already exist", childDto.getName(), childDto.getSurname()));
        }
        validatePhone(null, childDto.getPhone());
        Child child = childConverter.unconvert(childDto);
        childRepository.save(child);
        return findById(child.getId());
    }

    @Override
    @Transactional
    public ChildDto updateChild(ChildDto childDto) {
        Optional<Child> maybeChild = childRepository.findByNameAndSurname(childDto.getName(), childDto.getSurname());
        if(maybeChild.isPresent() && !maybeChild.get().getId().equals(childDto.getId())) {
            throw new ServiceException(String.format("Child with name %s and surname %s has already exist", childDto.getName(), childDto.getSurname()));
        }
        Child child = childRepository.getOne(childDto.getId());
        validatePhone(childDto.getId(), childDto.getPhone());
        child.setName(childDto.getName());
        child.setSurname(childDto.getSurname());
        child.setPhone(childDto.getPhone());
        child.setAge(childDto.getAge());
        ChildrenGroup childrenGroup = childrenGroupRepository.findByGroupNumber(childDto.getGroupNumber())
                .orElseThrow(() -> new ServiceException(String.format("There is no group with number %d", childDto.getGroupNumber())));
        child.setChildrenGroup(childrenGroup);
        return childrenService.findById(child.getId());
    }

    @Override
    @Transactional
    public void remove(Long id) {
        childRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ChildDataDto findChildDataDto(Long childId) {
        Child child = childRepository.findById(childId)
                .orElseThrow((() -> new ServiceException(String.format("There is no child with id %d",
                        childId))));
        ChildrenGroup childrenGroup =
                childrenGroupRepository.findByGroupNumber(child.getChildrenGroup().getGroupNumber())
                .orElseThrow(() -> new ServiceException(String.format("There is no children group with number %d",
                        childId)));
        return ChildDataDto.builder()
                .child(childConverter.convert(child))
                .childrenGroup(childrenGroupConverter.convert(childrenGroup))
                .build();
    }

    private void validatePhone(Long childid, String phone) {
        Optional<Child> maybeChild = childRepository.findByPhone(phone);
        if (maybeChild.isPresent() && !maybeChild.get().getId().equals(childid)) {
            throw new ServiceException(String.format("Child with phone %s has already exists", phone));
        }
    }
}
