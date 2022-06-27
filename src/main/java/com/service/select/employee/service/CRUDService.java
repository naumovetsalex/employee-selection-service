package com.service.select.employee.service;

import com.service.select.employee.DTO.jpa.DTOModel;

public interface CRUDService<T extends DTOModel> {

    long create(T t);

    T read(long id);

    void update(T t, long id);

    void delete(long id);
}
