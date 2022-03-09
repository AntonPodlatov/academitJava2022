package ru.academit.podlatov.temperature_converter.model;

public class Fahrenheit implements Scale {

    @Override
    public double convertToCelsius(double value) {
        return (value - 32) * (double) 5 / 9;
    }

    @Override
    public double convertFromCelsius(double value) {
        return (value * (double) 5 / 9) + 32;
    }

    @Override
    public String toString() {
        return "Fahrenheit";
    }
}
