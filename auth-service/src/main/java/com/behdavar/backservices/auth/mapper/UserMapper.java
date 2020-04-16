package com.behdavar.backservices.auth.mapper;

import com.behdavar.backservices.auth.entity.UserEntity;
import com.behdavar.backservices.auth.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper extends AuthBaseMapper<UserEntity, UserModel> {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
}
