package com.behdavar.backservices.auth.mapper;

import com.behdavar.backservices.auth.entity.ClientAuthorityEntity;
import com.behdavar.backservices.auth.model.ClientAuthorityModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClientAuthorityMapper extends AuthBaseMapper<ClientAuthorityEntity, ClientAuthorityModel> {
    ClientAuthorityMapper INSTANCE = Mappers.getMapper(ClientAuthorityMapper.class);
}
