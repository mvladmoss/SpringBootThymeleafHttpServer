package com.bsuir.second.model.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

import com.bsuir.second.model.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    private LocalDate birthdayDate;
    private String phone;

    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(
            name = "teacher_specialization",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "specialization_id"))
    private Set<Specialization> specializations;

}
