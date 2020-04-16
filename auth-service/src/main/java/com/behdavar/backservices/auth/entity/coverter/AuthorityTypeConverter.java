package com.behdavar.backservices.auth.entity.coverter;

import com.behdavar.backservices.auth.enums.AuthorityTypeEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

/**
 * @author Abbas Alishirvani
 */
@Converter
public class AuthorityTypeConverter implements AttributeConverter<AuthorityTypeEnum, String> {

    @Override
    public String convertToDatabaseColumn(AuthorityTypeEnum accountTypeEnum) {
        if (Objects.isNull(accountTypeEnum)) return null;
        return accountTypeEnum.getCode();
    }

    @Override
    public AuthorityTypeEnum convertToEntityAttribute(String code) {
        return AuthorityTypeEnum.findByCode(code);
    }
}
