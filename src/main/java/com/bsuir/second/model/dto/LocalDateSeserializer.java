package com.bsuir.second.model.dto;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class LocalDateSeserializer extends JsonDeserializer<LocalDate> {

    private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneOffset.UTC);

    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext context) throws IOException {
        return LocalDate.from(fmt.parse(p.getText()));
    }
}