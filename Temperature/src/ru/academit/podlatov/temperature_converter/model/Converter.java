package ru.academit.podlatov.temperature_converter.model;

import ru.academit.podlatov.temperature_converter.model.scales.Scale;

public class Converter {
    public double convert(Scale inputScale, Scale outputScale, double value) {
        return outputScale.convertFromCelsius(inputScale.convertToCelsius(value));
    }
}