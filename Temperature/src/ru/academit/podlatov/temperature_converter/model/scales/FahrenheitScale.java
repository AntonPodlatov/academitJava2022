package ru.academit.podlatov.temperature_converter.model.scales;

public class FahrenheitScale implements Scale {
    @Override
    public double convertToCelsius(double value) {
        return (value - 32) / 1.8;
    }

    @Override
    public double convertFromCelsius(double value) {
        return value * 9 / 5 + 32;
    }

    @Override
    public String toString() {
        return "Fahrenheit";
    }
}