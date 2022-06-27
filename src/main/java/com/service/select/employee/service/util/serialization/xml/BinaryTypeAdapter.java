package com.service.select.employee.service.util.serialization.xml;

import com.service.select.employee.util.Base64Util;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class BinaryTypeAdapter extends XmlAdapter<String, byte[]> {

    @Override
    public byte[] unmarshal(String encoded) throws Exception {
        return Base64Util.DECODER.decode(encoded);
    }

    @Override
    public String marshal(byte[] src) throws Exception {
        return Base64Util.ENCODER.encodeToString(src);
    }
}
