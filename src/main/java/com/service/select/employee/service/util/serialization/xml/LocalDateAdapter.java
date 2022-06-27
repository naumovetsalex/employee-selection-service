package com.service.select.employee.service.util.serialization.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LocalDateAdapter extends XmlAdapter<String, Date> {

    private static final String CUSTOM_FORMAT_STRING = "yyyy-MM-dd";
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat(CUSTOM_FORMAT_STRING);
    ;

    @Override
    public String marshal(Date date) {
        return FORMATTER.format(date);
    }

    @Override
    public Date unmarshal(String date) throws ParseException {
        return FORMATTER.parse(date);
    }

}
