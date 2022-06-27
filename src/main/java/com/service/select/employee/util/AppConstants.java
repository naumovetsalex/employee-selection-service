package com.service.select.employee.util;

import java.text.SimpleDateFormat;

public class AppConstants {

    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "20";
    public static final String DEFAULT_SORT_BY = "id";
    public static final String DEFAULT_SORT_DIRECTION = "asc";
    public static final int MIN_VALUE_POSITION_MATCH = 0;
    public static final boolean IS_NOT_AVATAR = false;
    public static final boolean IS_AVATAR = true;
    public static final String MINUS_SYMBOL = "-";
    public static final String COMMA_SYMBOL = ",";
    public static final String PLUS_SYMBOL = "+";
    public static final String SPACE_SYMBOL = " ";
    public static final String SORT_PARAM_PAGINATION = "sort";
    public static final String OFFSET_PARAM_PAGINATION = "offset";
    public static final String LIMIT_PARAM_PAGINATION = "limit";
    public static final String SEARCH_PARAM = "search";
    public static final String FIELDS_PARAM = "fields";
    public static final String QUERY_PARAM = "q";
    public static final String EMPTY_STRING = "";
    public static final String UNIX_TIME_START = "1970-01-01";
    private static final String CUSTOM_FORMAT_STRING = "yyyy-MM-dd";
    public static final SimpleDateFormat FORMATTER = new SimpleDateFormat(CUSTOM_FORMAT_STRING);

}
