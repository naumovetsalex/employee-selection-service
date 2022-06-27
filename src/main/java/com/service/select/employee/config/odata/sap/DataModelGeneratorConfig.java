package com.service.select.employee.config.odata.sap;

import com.sap.cloud.sdk.datamodel.odata.utility.NameSource;
import com.sap.cloud.sdk.datamodel.odatav4.generator.DataModelGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class DataModelGeneratorConfig {

    @Autowired
    DataModelGeneratorProperties dataModelGeneratorProperties;

    @Bean
    public void configOdataGenerator() {
        final Path inputDirectory = Paths.get(dataModelGeneratorProperties.getInputDirectoryPath());
        final Path outputDirectory = Paths.get(dataModelGeneratorProperties.getOutputDirectoryPath());
        final Path serviceNameMapping = inputDirectory.resolve(dataModelGeneratorProperties.getServiceNameMappingFile());
        new DataModelGenerator()
                .overwriteFiles(true)
                .withInputDirectory(inputDirectory.toFile())
                .withOutputDirectory(outputDirectory.toFile())
                .withServiceNameMapping(serviceNameMapping.toFile())
                .pojosOnly(false)
                .withNameSource(NameSource.NAME)
                .withPackageName(dataModelGeneratorProperties.getPackageName())
                .withDefaultBasePath(dataModelGeneratorProperties.getDefaultBasePath())
                .serviceMethodsPerEntitySet()
                .keepExistingSignatures(true)
                .execute();
    }
}
