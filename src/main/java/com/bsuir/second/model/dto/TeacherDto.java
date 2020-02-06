package com.bsuir.second.model.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

import com.bsuir.second.model.GenderEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TeacherDto {

    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private GenderEnum gender;
    @JsonSerialize(using = InstantSerializer.class)
    @JsonDeserialize(using = LocalDateSeserializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdayDate;
    private String phone;
    @NotEmpty
    private Set<SpecializationDto> specializations;
}
