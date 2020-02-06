package com.bsuir.second.model.dto;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
public class ChildrenGroupData {

    @NotNull
    @JsonSerialize(using = InstantSerializer.class)
    @JsonDeserialize(using = LocalDateSeserializer.class)
    private Instant currentData;
    @NotNull
    private List<ChildDto> children;

}
