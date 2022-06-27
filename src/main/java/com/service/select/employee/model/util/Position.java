package com.service.select.employee.model.util;

import java.util.stream.Stream;

public enum Position {

    INTERN(1, "intern"),
    JUNIOR(2, "junior"),
    STRONG_JUNIOR(3, "strong junior"),
    MIDDLE(4, "middle"),
    DEVELOPER(4, "developer"),
    SOFTWARE(4, "software"),
    STRONG_MIDDLE(5, "strong middle"),
    SENIOR(6, "senior"),
    EXPERT(7, "expert");

    public static final int COUNT_OF_POSITIONS = 7;
    private final int positionNumber;
    private final String nameOfPosition;

    Position(int positionNumber, String nameOfPosition) {
        this.positionNumber = positionNumber;
        this.nameOfPosition = nameOfPosition;
    }

    public static Position definePosition(int positionNumber) {
        return Stream.of(Position.values())
                .filter(o -> o.positionNumber == positionNumber)
                .findFirst()
                .orElse(null);
    }

    public int getPositionNumber() {
        return positionNumber;
    }

    public String getNameOfPosition() {
        return nameOfPosition;
    }
}
