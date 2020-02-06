package com.bsuir.second.repository;

import java.util.Optional;

import com.bsuir.second.model.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Optional<Teacher> findByPhone(String phone);

    Optional<Teacher> findByNameAndSurname(String name, String surname);

    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM teacher")
    Long countOfAllTeachers();
}
