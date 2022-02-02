package ru.academit.podlatov.shapes.comparison_functions;

public class PositivityChecker {
    public static void check(double number) {
        if (number <= 0) {
            throw new IllegalArgumentException("number = " + number + ". The figure dimension cannot be less than or equal to zero.");
        }
    }
}
