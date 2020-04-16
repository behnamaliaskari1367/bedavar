package com.behdavar.backservices.auth.entity.coverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Abbas Alishirvani
 */
@Converter
public class StringToLinesConverter implements AttributeConverter<Set<String>, String> {

    @Override
    public String convertToDatabaseColumn(Set<String> set) {
        if (Objects.isNull(set) || set.isEmpty()) {
            return null;
        }

        return String.join("\r\n", set);
    }

    @Override
    public Set<String> convertToEntityAttribute(String s) {
        if (Objects.isNull(s) || s.isEmpty()) {
            return null;
        }

        return Arrays.stream(s.split("\r\n"))
                .collect(Collectors.toSet());
    }
}
