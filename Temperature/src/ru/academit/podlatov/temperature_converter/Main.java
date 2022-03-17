package ru.academit.podlatov.temperature_converter;

import ru.academit.podlatov.temperature_converter.model.Converter;
import ru.academit.podlatov.temperature_converter.model.scales.CelsiusScale;
import ru.academit.podlatov.temperature_converter.model.scales.FahrenheitScale;
import ru.academit.podlatov.temperature_converter.model.scales.KelvinScale;
import ru.academit.podlatov.temperature_converter.model.scales.Scale;
import ru.academit.podlatov.temperature_converter.view.Window;


public class Main {
    public static void main(String[] args) {
        Window window = new Window(new Scale[]{new CelsiusScale(), new FahrenheitScale(), new KelvinScale()}, new Converter());
        window.start();
    }
}