package com.capgemini.training.mappers;

import com.capgemini.training.dtos.get.CounseleesDTO;
import com.capgemini.training.dtos.get.CounselorDTO;
import com.capgemini.training.dtos.get.EmployeeGetDTO;
import com.capgemini.training.dtos.post.EmployeePostDTO;
import com.capgemini.training.models.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);


    EmployeePostDTO employeeToEmployeePostDto(Employee employee);

    Employee employeeDtoToEmployee(EmployeePostDTO employeePostDTO);

    EmployeeGetDTO employeeToEmployeeGetDto(Employee employee);

    CounseleesDTO employeeToCounseleeDto(Employee employee);


    CounselorDTO employeeToCounselorDto(Employee employee);
    List<EmployeeGetDTO> employeesListToEmployeesGetListDto(Iterable<Employee> employees);

}
