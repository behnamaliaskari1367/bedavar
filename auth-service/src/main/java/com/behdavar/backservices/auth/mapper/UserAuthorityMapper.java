package com.behdavar.backservices.auth.mapper;

import com.behdavar.backservices.auth.entity.UserAuthorityEntity;
import com.behdavar.backservices.auth.model.UserAuthorityModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserAuthorityMapper extends AuthBaseMapper<UserAuthorityEntity, UserAuthorityModel> {
    UserAuthorityMapper INSTANCE = Mappers.getMapper(UserAuthorityMapper.class);
}
