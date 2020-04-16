package com.behdavar.backservices.auth.mapper;

import com.behdavar.backservices.auth.model.ClientModel;
import com.behdavar.backservices.auth.entity.ClientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClientDetailsMapper extends AuthBaseMapper<ClientEntity, ClientModel> {
    ClientDetailsMapper INSTANCE = Mappers.getMapper(ClientDetailsMapper.class);
}
