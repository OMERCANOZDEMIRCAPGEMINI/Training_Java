package com.capgemini.training.mappers;

import com.capgemini.training.dtos.post.UnitPostDTO;
import com.capgemini.training.models.Unit;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UnitMapper {
    UnitMapper INSTANCE = Mappers.getMapper( UnitMapper.class );

    UnitPostDTO unitToUnitDto(Unit unit);
    Unit unitDtoToUnit(UnitPostDTO unitPostDTO);
}
