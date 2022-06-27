package com.service.select.employee.repository.jpa;

import com.service.select.employee.model.jpa.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    boolean existsByContent(byte[] content);

    boolean existsByIdAndEmployeeId(long id, long employeeId);

}
