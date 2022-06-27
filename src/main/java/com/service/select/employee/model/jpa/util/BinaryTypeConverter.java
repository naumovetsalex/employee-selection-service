package com.service.select.employee.model.jpa.util;

import com.service.select.employee.util.Base64Util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BinaryTypeConverter implements AttributeConverter<String, byte[]> {

    @Override
    public byte[] convertToDatabaseColumn(String attribute) {
        return Base64Util.DECODER.decode(attribute);
    }

    @Override
    public String convertToEntityAttribute(byte[] dbData) {
        return Base64Util.ENCODER.encodeToString(dbData);
    }
}
