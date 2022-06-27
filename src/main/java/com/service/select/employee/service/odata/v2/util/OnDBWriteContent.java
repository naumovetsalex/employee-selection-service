package com.service.select.employee.service.odata.v2.util;

import lombok.SneakyThrows;
import org.apache.olingo.odata2.jpa.processor.api.OnJPAWriteContent;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialClob;
import java.sql.Blob;
import java.sql.Clob;

@Profile("odatav2")
@Component
public class OnDBWriteContent implements OnJPAWriteContent {
    @SneakyThrows
    @Override
    public Blob getJPABlob(byte[] binaryData) throws ODataJPARuntimeException {
        return new SerialBlob(binaryData);
    }

    @SneakyThrows
    @Override
    public Clob getJPAClob(char[] characterData) throws ODataJPARuntimeException {
        return new SerialClob(characterData);
    }
}