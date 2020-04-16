package com.behdavar.backservices.auth.entity.coverter;

import com.behdavar.backservices.auth.enums.AuthorizedGrantTypeEnum;

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
public class AuthorizedGrantTypeConverter implements AttributeConverter<Set<AuthorizedGrantTypeEnum>, String> {

    @Override
    public String convertToDatabaseColumn(Set<AuthorizedGrantTypeEnum> set) {
        if (Objects.isNull(set) || set.isEmpty()) {
            return null;
        }

        return set.stream()
                .map(AuthorizedGrantTypeEnum::getGrantType)
                .collect(Collectors.joining(","));
    }

    @Override
    public Set<AuthorizedGrantTypeEnum> convertToEntityAttribute(String s) {
        if (Objects.isNull(s) || s.isEmpty()) {
            return null;
        }

        return Arrays.stream(s.split(","))
                .map(AuthorizedGrantTypeEnum::findByGrantType)
                .collect(Collectors.toSet());
    }
}
