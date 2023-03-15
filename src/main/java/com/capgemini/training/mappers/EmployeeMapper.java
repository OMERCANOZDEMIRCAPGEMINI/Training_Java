package com.capgemini.training.mappers;

import com.capgemini.training.dtos.EmployeeDTO;
import com.capgemini.training.models.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper( EmployeeMapper.class );

    EmployeeDTO employeeToEmployeeDto(Employee employee);
    Employee employeeDtoToEmployee(EmployeeDTO employeeDTO);
}
