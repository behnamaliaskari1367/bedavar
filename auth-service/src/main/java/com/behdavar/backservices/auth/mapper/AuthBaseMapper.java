package com.behdavar.backservices.auth.mapper;

import com.behdavar.backservices.common.model.BaseEntity;
import com.behdavar.backservices.common.model.BaseModel;
import org.mapstruct.Context;

import java.util.List;

/**
 * @author Abbas Alishirvani
 */
public interface AuthBaseMapper<ENTITY extends BaseEntity, MODEL extends BaseModel> {

    List<MODEL> entitiesToModels(List<ENTITY> entities, @Context CycleAvoidingMappingContext context);

    List<ENTITY> modelToEntities(List<MODEL> models, @Context CycleAvoidingMappingContext context);

    MODEL entityToModel(ENTITY entity, @Context CycleAvoidingMappingContext context);

    ENTITY modelToEntity(MODEL model, @Context CycleAvoidingMappingContext context);
}
