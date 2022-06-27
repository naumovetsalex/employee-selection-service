package com.service.select.employee.service.util;

import com.service.select.employee.model.util.CompanyWorkExperience;
import com.service.select.employee.model.util.Position;
import com.service.select.employee.model.util.SeniorityLevel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ComputingPositionMatchService {

    private static final int COUNT_OF_MONTH_IN_YEAR = 12;
    private static final double NO_EXPERIENCE = 0.0;
    private static final double QUARTER_OF_YEAR = 0.25;
    private static final double HALF_OF_YEAR = 0.5;
    private static final double ONE_YEAR = 1.0;
    private static final int COUNT_OF_PERCENT = 100;
    private static final double AVERAGE_COEFFICIENT_BETWEEN_POSITION_AND_SENIORITY_LEVEL_AND_COMPANY_WORK_EXPERIENCE = 1.0 / 3.0;

    public double computePositionMatches(LocalDate companyWorkExperience, String position, String seniorityLevel) {
        double result = computeCompanyWorkExperiencePercentMatches(companyWorkExperience)
                + computePositionPercentMatches(position)
                + computeSeniorityLevelPercentMatches(seniorityLevel);
        return result * COUNT_OF_PERCENT;
    }

    private double computeCompanyWorkExperiencePercentMatches(LocalDate companyWorkExperience) {
        long countOfMonth = ChronoUnit.MONTHS.between(companyWorkExperience, LocalDate.now());
        double countOfYears = ((double) countOfMonth) / COUNT_OF_MONTH_IN_YEAR;
        double approximateCountOfYears = approximatingFloatingPart(countOfYears);
        final double COEFFICIENT_FOR_COMPANY_WORK_EXPERIENCE = 1.0 / CompanyWorkExperience.values().length;
        if (CompanyWorkExperience.defineCompanyWorkExperience(approximateCountOfYears) != null) {
            return (approximateCountOfYears * 2 * COEFFICIENT_FOR_COMPANY_WORK_EXPERIENCE)
                    * AVERAGE_COEFFICIENT_BETWEEN_POSITION_AND_SENIORITY_LEVEL_AND_COMPANY_WORK_EXPERIENCE;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    private double approximatingFloatingPart(double value) {
        long downCastedValue = (long) value;
        double floatingPart = value - downCastedValue;
        if (Math.abs(floatingPart) <= QUARTER_OF_YEAR) {
            return NO_EXPERIENCE;
        }
        if (Math.abs(floatingPart) > QUARTER_OF_YEAR) {
            return HALF_OF_YEAR;
        }
        if (Math.abs(floatingPart) > HALF_OF_YEAR) {
            return ONE_YEAR;
        } else {
            return HALF_OF_YEAR;
        }
    }

    private double computePositionPercentMatches(String position) {
        final double COEFFICIENT_FOR_POSITION_PERCENT = 1.0 / Position.COUNT_OF_POSITIONS;
        AtomicInteger positionNumber = new AtomicInteger();
        String regex = "[a-zA-Z \\t\\d\\s,.]*";
        Arrays.stream(Position.values()).forEach(positionValue -> {
            Pattern pattern = Pattern.compile(regex +
                    positionValue.getNameOfPosition().toLowerCase(Locale.ROOT) + regex, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(position);
            if (matcher.find()) {
                positionNumber.set(positionValue.getPositionNumber());
            }
        });
        return positionNumber.get() * COEFFICIENT_FOR_POSITION_PERCENT
                * AVERAGE_COEFFICIENT_BETWEEN_POSITION_AND_SENIORITY_LEVEL_AND_COMPANY_WORK_EXPERIENCE;
    }

    private double computeSeniorityLevelPercentMatches(String seniorityLevel) {
        if (SeniorityLevel.isSeniorityLevel(seniorityLevel)) {
            int position = SeniorityLevel.defineSeniorityLevel(seniorityLevel).getPosition();
            final double COEFFICIENT_FOR_SENIORITY_LEVEL = 1.0 / SeniorityLevel.values().length;
            return position * COEFFICIENT_FOR_SENIORITY_LEVEL
                    * AVERAGE_COEFFICIENT_BETWEEN_POSITION_AND_SENIORITY_LEVEL_AND_COMPANY_WORK_EXPERIENCE;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
