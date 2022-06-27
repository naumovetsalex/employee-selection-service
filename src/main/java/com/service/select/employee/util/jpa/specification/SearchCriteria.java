package com.service.select.employee.util.jpa.specification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@AllArgsConstructor
public class SearchCriteria {
    private String key;
    private String operation;
    private Object value;
}
