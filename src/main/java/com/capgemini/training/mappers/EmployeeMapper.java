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

    @Mapping(source = "counselor.id", target = "counselorId")
    EmployeePostDTO employeeToEmployeePostDto(Employee employee);

    Employee employeeDtoToEmployee(EmployeePostDTO employeePostDTO);

    EmployeeGetDTO employeeToEmployeeGetDto(Employee employee);
    @Mapping(source = "unit.name",target = "unit")
    CounseleesDTO employeeToCounseleeDto(Employee employee);
    @Mapping(source = "unit.name",target = "unit")

    CounselorDTO employeeToCounselorDto(Employee employee);
    List<EmployeeGetDTO> employeesListToEmployeesGetListDto(Iterable<Employee> employees);

}
