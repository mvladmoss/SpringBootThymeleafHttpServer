package com.bsuir.second.exception;

import java.time.Instant;
import java.util.List;

import com.bsuir.second.model.dto.InstantSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {

    private final String code;

    @JsonSerialize(using = InstantSerializer.class)
    private final Instant occurredAt = Instant.now();
    private final String description;
    private List<Detail> details;

    public Error(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public Error(String code, String description, List<Detail> details) {
        this.code = code;
        this.description = description;
        this.details = details;
    }

    @Data
    @AllArgsConstructor
    public static class Detail {
        private String code;
        private String description;
    }
}
