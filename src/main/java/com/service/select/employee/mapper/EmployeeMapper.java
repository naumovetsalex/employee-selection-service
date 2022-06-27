package com.service.select.employee.mapper;

import com.service.select.employee.DTO.jpa.EmployeeDTO;
import com.service.select.employee.model.jpa.Employee;
import com.service.select.employee.service.impl.EmployeeServiceImpl;
import com.service.select.employee.service.impl.ImageServiceImpl;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {EmployeeServiceImpl.class, ImageServiceImpl.class})
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    @Mapping(target = "image", ignore = true)
    @Mapping(target = "positionMatch", ignore = true)
    EmployeeDTO employeeToEmployeeDTO(Employee employee);

    @Mapping(target = "images", ignore = true)
    @Mapping(target = "positionMatch", ignore = true)
    Employee employeeDTOToEmployee(EmployeeDTO employeeDTO);

    @Mapping(target = "images", ignore = true)
    @Mapping(target = "positionMatch", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEmployeeFromEmployeeDTO(EmployeeDTO employeeDTO, @MappingTarget Employee employee);
}
