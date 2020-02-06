package com.bsuir.second.repository;

import java.util.List;
import java.util.Optional;

import com.bsuir.second.model.entity.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChildRepository extends JpaRepository<Child, Long> {

    Optional<Child> findByPhone(String phone);

    Optional<Child> findByNameAndSurname(String name, String surname);

    @Query("SELECT ch FROM Child ch WHERE ch.childrenGroup.id = :id")
    List<Child> findByGroupId(@Param("id") Long id);

    @Query(nativeQuery = true, value = "SELECT COUNT(*) from child")
    Long countOfAllChildren();
}
