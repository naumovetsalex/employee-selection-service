package com.service.select.employee.service.odata.v2;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("odatav2")
@Component
public class ODataJpaServiceFactory extends CustomODataServiceFactory {
}
