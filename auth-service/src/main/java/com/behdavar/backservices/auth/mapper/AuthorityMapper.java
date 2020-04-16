package com.behdavar.backservices.auth.mapper;

import com.behdavar.backservices.auth.entity.AuthorityEntity;
import com.behdavar.backservices.auth.model.AuthorityModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AuthorityMapper extends AuthBaseMapper<AuthorityEntity, AuthorityModel> {

    AuthorityMapper INSTANCE = Mappers.getMapper(AuthorityMapper.class);
}
