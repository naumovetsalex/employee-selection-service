package com.service.select.employee.mapper;

import com.service.select.employee.DTO.jpa.EmployeeDTO;
import com.service.select.employee.model.jpa.Employee;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-31T19:36:22+0200",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.2.jar, environment: Java 18.0.1.1 (Oracle Corporation)"
)
@Component
public class EmployeeMapperImpl implements EmployeeMapper {

    @Override
    public EmployeeDTO employeeToEmployeeDTO(Employee employee) {
        if ( employee == null ) {
            return null;
        }

        EmployeeDTO employeeDTO = new EmployeeDTO();

        employeeDTO.setId( employee.getId() );
        employeeDTO.setName( employee.getName() );
        employeeDTO.setPosition( employee.getPosition() );
        employeeDTO.setDepartment( employee.getDepartment() );
        employeeDTO.setSeniorityLevel( employee.getSeniorityLevel() );
        employeeDTO.setLocation( employee.getLocation() );
        if ( employee.getCompanyWorkExperience() != null ) {
            employeeDTO.setCompanyWorkExperience( LocalDateTime.ofInstant( employee.getCompanyWorkExperience().toInstant(), ZoneOffset.UTC ).toLocalDate() );
        }

        return employeeDTO;
    }

    @Override
    public Employee employeeDTOToEmployee(EmployeeDTO employeeDTO) {
        if ( employeeDTO == null ) {
            return null;
        }

        Employee employee = new Employee();

        if ( employeeDTO.getId() != null ) {
            employee.setId( employeeDTO.getId() );
        }
        employee.setName( employeeDTO.getName() );
        employee.setPosition( employeeDTO.getPosition() );
        employee.setDepartment( employeeDTO.getDepartment() );
        employee.setSeniorityLevel( employeeDTO.getSeniorityLevel() );
        employee.setLocation( employeeDTO.getLocation() );
        if ( employeeDTO.getCompanyWorkExperience() != null ) {
            employee.setCompanyWorkExperience( Date.from( employeeDTO.getCompanyWorkExperience().atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
        }

        return employee;
    }

    @Override
    public void updateEmployeeFromEmployeeDTO(EmployeeDTO employeeDTO, Employee employee) {
        if ( employeeDTO == null ) {
            return;
        }

        if ( employeeDTO.getId() != null ) {
            employee.setId( employeeDTO.getId() );
        }
        if ( employeeDTO.getName() != null ) {
            employee.setName( employeeDTO.getName() );
        }
        if ( employeeDTO.getPosition() != null ) {
            employee.setPosition( employeeDTO.getPosition() );
        }
        if ( employeeDTO.getDepartment() != null ) {
            employee.setDepartment( employeeDTO.getDepartment() );
        }
        if ( employeeDTO.getSeniorityLevel() != null ) {
            employee.setSeniorityLevel( employeeDTO.getSeniorityLevel() );
        }
        if ( employeeDTO.getLocation() != null ) {
            employee.setLocation( employeeDTO.getLocation() );
        }
        if ( employeeDTO.getCompanyWorkExperience() != null ) {
            employee.setCompanyWorkExperience( Date.from( employeeDTO.getCompanyWorkExperience().atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
        }
    }
}
