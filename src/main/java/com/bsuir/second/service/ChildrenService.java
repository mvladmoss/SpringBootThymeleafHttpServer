package com.bsuir.second.service;

import java.util.List;

import com.bsuir.second.model.dto.ChildDataDto;
import com.bsuir.second.model.dto.ChildDto;

public interface ChildrenService {

    ChildDto findById(Long id);

    ChildDataDto findByNameAndSurname(String name, String surname);

    List<ChildDataDto> findAll();

    ChildDto saveChild(ChildDto childDto);

    ChildDto updateChild(ChildDto childDto);

    void remove(Long id);

    ChildDataDto findChildDataDto(Long childId);
}
