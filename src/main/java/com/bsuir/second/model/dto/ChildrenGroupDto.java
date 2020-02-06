package com.bsuir.second.model.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ChildrenGroupDto {

    private Long id;
    @NotNull
    private Integer groupNumber;
    @NotNull
    private String groupName;
    @NotNull
    private TeacherDto teacher;
    @NotNull
    private Integer ageGroup;
}
