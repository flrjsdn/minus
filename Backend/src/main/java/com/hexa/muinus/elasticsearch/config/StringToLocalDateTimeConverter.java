package com.hexa.muinus.elasticsearch.config;

import org.springframework.core.convert.converter.Converter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public LocalDateTime convert(String source) {
        return LocalDateTime.parse(source, formatter);  // String -> LocalDateTime 변환
    }
}
