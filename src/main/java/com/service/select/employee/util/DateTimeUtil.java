package com.service.select.employee.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeUtil {

    public static String getUTCDateFormattedToISO() {
        final String ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS_zzz";
        final SimpleDateFormat sdf = new SimpleDateFormat(ISO_FORMAT);
        final TimeZone utc = TimeZone.getTimeZone("UTC");
        sdf.setTimeZone(utc);
        return sdf.format(new Date());
    }
}
