package com.service.select.employee.repository.jpa;

import com.service.select.employee.model.jpa.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>,
        PagingAndSortingRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

    Page<Employee> findAll(Pageable pageable);

    @Query(value = "select * from employees where cast(company_work_experience as varchar) ~ ?1", nativeQuery = true)
    List<Employee> findAllByCompanyWorkExperienceCastToStringMatchingWithSearchValue(String companyWorkExperience);

    @Query(value = "select * from employees where cast(position_match as varchar) ~ ?1", nativeQuery = true)
    List<Employee> findAllByPositionMatchCastToStringMatchingWithSearchValue(String positionMatch);

    @Query(value = "select * from employees e where e.name ilike '%' || ?1 || '%' " +
            "or e.position ilike '%' || ?1 || '%' or e.department ilike '%' || ?1 || '%' " +
            "or e.seniority_level ilike '%' || ?1 || '%' or e.location ilike '%' || ?1 || '%'",
            nativeQuery = true)
    List<Employee> findAllByNameOrPositionOrDepartmentOrSeniorityLevelOrLocationMatchingWithSearchValue(
            String searchValue);


    List<Employee> findAllByNameOrPositionOrDepartmentOrSeniorityLevelOrLocationContainingIgnoreCase(
            String name, String position, String department, String seniorityLevel, String location);

    default List<Employee> findBySearchValueContains(String searchValue) {
        var companyWorkExperienceMatches = searchValue.matches("\\d+");
        var positionMatchMatches = searchValue.matches("\\d+\\.\\d+");
        if (positionMatchMatches || companyWorkExperienceMatches) {
            var employeesByCompanyWorkExperience = findAllByCompanyWorkExperienceCastToStringMatchingWithSearchValue(searchValue);
            var employeesByPositionMatch = findAllByPositionMatchCastToStringMatchingWithSearchValue(searchValue);
            employeesByCompanyWorkExperience.addAll(employeesByPositionMatch);
            return employeesByCompanyWorkExperience;
        } else {
            return findAllByNameOrPositionOrDepartmentOrSeniorityLevelOrLocationMatchingWithSearchValue(searchValue);
        }
    }

    boolean existsById(long id);
}
