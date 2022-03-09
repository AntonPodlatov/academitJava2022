package ru.academit.podlatov.temperature_converter.controller;

import ru.academit.podlatov.temperature_converter.model.Scale;

public class Converter {
    public double convert(Scale input, Scale output, double value) {
        return Math.round(output.convertFromCelsius(input.convertToCelsius(value)) * 100.0) / 100.0;
    }
}
