package ru.academit.podlatov.temperature_converter.model.scales;

public interface Scale {
    double convertToCelsius(double value);

    double convertFromCelsius(double value);
}