package com.bsuir.second.repository;

import java.util.Optional;

import com.bsuir.second.model.entity.ChildrenGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildrenGroupRepository extends JpaRepository<ChildrenGroup, Long> {

    Optional<ChildrenGroup> findByGroupNumberAndGroupName(Integer groupNumber, String groupName);

    Optional<ChildrenGroup> findByGroupNumber(Integer groupNumber);
}
