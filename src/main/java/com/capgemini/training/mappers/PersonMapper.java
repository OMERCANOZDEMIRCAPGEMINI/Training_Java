package com.capgemini.training.mappers;

import com.capgemini.training.dtos.EmployeeDTO;
import com.capgemini.training.models.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper( PersonMapper.class );

    EmployeeDTO personToPersonDto(Employee employee);
    Employee personDtoToPerson(EmployeeDTO employeeDTO);
}
