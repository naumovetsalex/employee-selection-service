package com.service.select.employee.config.odata.sap;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties
@PropertySource("classpath:application.yml")
@ConfigurationProperties("spring.sap-cloud-sdk.odata.v4")
@Data
public class DataModelGeneratorProperties {

    private String inputDirectoryPath;
    private String outputDirectoryPath;
    private String serviceNameMappingFile;
    private String packageName;
    private String defaultBasePath;

}
