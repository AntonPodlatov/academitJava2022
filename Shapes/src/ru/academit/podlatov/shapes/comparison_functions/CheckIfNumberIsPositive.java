package ru.academit.podlatov.shapes.comparison_functions;

public class CheckIfNumberIsPositive {
    public static void check(double number) {
        if (number < 0) {
            throw new IllegalArgumentException("The figure dimension cannot be less than or equal to zero.");
        }
    }
}
