package com.bsuir.second.service;

import java.util.List;

import com.bsuir.second.model.dto.ChildrenGroupData;
import com.bsuir.second.model.dto.ChildrenGroupDto;

public interface ChildrenGroupService {

    ChildrenGroupData findChildrenGroupData(Integer groupNumber);

    ChildrenGroupDto save(ChildrenGroupDto childrenGroupDto);

    ChildrenGroupDto update(ChildrenGroupDto childrenGroupDto);

    List<ChildrenGroupDto> findAll();

    ChildrenGroupDto findByGroupNumber(Integer id);

    ChildrenGroupDto findDataById(Long id);

    void remove(Long id);
}
