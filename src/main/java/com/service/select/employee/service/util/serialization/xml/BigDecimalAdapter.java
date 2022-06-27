package com.service.select.employee.service.util.serialization.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.math.BigDecimal;

public class BigDecimalAdapter extends XmlAdapter<String, BigDecimal> {

    @Override
    public String marshal(BigDecimal bigDecimal) {
        return bigDecimal.toEngineeringString();
    }

    @Override
    public BigDecimal unmarshal(String bigDecimal) {
        return BigDecimal.valueOf(Double.parseDouble(bigDecimal));
    }
}
