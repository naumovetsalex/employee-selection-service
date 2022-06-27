package com.service.select.employee.model.util;

import lombok.Data;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum CompanyWorkExperience {

    HALF_OF_YEAR(0.5d), YEAR(1d), YEAR_AND_HALF(1.5d),
    TWO_YEARS(2d), TWO_AND_HALF_YEARS(2.5d),
    THREE_YEARS(3d), THREE_AND_HALF_YEARS(3.5d),
    FOUR_YEARS(4d), FOUR_AND_HALF_YEARS(4.5d),
    FIVE_YEARS(5d), FIVE_AND_HALF_YEARS(5.5d),
    SIX_YEARS(6d), SIX_AND_HALF_YEARS(6.5d),
    SEVEN_YEARS(7d), SEVEN_AND_HALF_YEARS(7.5d),
    EIGHT_YEARS(8d), EIGHT_AND_HALF_YEARS(8.5d),
    NINE_YEARS(9d), NINE_AND_HALF_YEARS(9.5d),
    TEN_YEARS(10d), TEN_AND_HALF_YEARS(10.5d),
    ELEVEN_YEARS(11d), ELEVEN_AND_HALF_YEARS(11.5d),
    TWELVE_YEARS(12d), TWELVE_AND_HALF_YEARS(12.5d),
    THIRTEEN_YEARS(13d), THIRTEEN_AND_HALF_YEARS(13.5d),
    FOURTEEN_YEARS(14d), FOURTEEN_AND_HALF_YEARS(14.5d),
    FIFTEEN_YEARS(15d);

    private final double countOfYears;

    CompanyWorkExperience(double countOfYears) {
        this.countOfYears = countOfYears;
    }

    public static CompanyWorkExperience defineCompanyWorkExperience(double countOfYears) {
        return Stream.of(CompanyWorkExperience.values())
                .filter(o -> o.countOfYears == countOfYears)
                .findFirst()
                .orElse(null);
    }
}
