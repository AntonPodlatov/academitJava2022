package ru.academit.podlatov.shapes.comparison_functions;

public class CheckIfDoublesIsEqual {
    public static boolean isEqual(double double1, double double2) {
        final double epsilon = 1.0e-10;

        return Math.abs(double1 - double2) <= epsilon;
    }
}
