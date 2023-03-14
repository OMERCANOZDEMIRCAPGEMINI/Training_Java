package com.capgemini.training.mappers;

import com.capgemini.training.dtos.EmployeeDTO;
import com.capgemini.training.dtos.UnitDTO;
import com.capgemini.training.models.Employee;
import com.capgemini.training.models.Unit;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UnitMapper {
    UnitMapper INSTANCE = Mappers.getMapper( UnitMapper.class );

    UnitDTO unitToUnitDto(Unit unit);
    Unit unitDtoToUnit(UnitDTO unitDTO);
}
