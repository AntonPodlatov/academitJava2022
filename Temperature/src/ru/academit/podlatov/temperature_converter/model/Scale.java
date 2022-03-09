package ru.academit.podlatov.temperature_converter.model;

public interface Scale {
    double convertToCelsius(double value);
    double convertFromCelsius(double value);
}