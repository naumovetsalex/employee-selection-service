package com.service.select.employee.model.util;

import lombok.Data;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum SeniorityLevel {

    D11("D1, 1", 1), D12("D1, 2", 2),
    D13("D1, 3", 3), D21("D2, 1", 4),
    D22("D2, 2", 5), D23("D2, 3", 6),
    D31("D3, 1", 7), D32("D3, 2", 8),
    D33("D3, 3", 9);

    private final String seniorityLevelPosition;
    private final int position;

    SeniorityLevel(String seniorityLevelPosition, int position) {
        this.seniorityLevelPosition = seniorityLevelPosition;
        this.position = position;
    }

    public static SeniorityLevel defineSeniorityLevel(String seniorityLevelPosition) {
        return Stream.of(SeniorityLevel.values())
                .filter(o -> o.seniorityLevelPosition.equalsIgnoreCase(seniorityLevelPosition))
                .findFirst()
                .orElse(null);
    }

    public static boolean isSeniorityLevel(String seniorityLevelPosition) {
        return Stream.of(SeniorityLevel.values())
                .anyMatch(o -> o.seniorityLevelPosition.equalsIgnoreCase(seniorityLevelPosition));
    }

    public int getPosition() {
        return position;
    }
}
