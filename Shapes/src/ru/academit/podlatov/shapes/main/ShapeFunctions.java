package ru.academit.podlatov.shapes.main;

import ru.academit.podlatov.shapes.comparators.AreaComparator;
import ru.academit.podlatov.shapes.comparators.PerimeterComparator;
import ru.academit.podlatov.shapes.Shape;

import java.util.Arrays;

public class ShapeFunctions {
    public static Shape getMaxAreaShape(Shape[] shapes) {
        if (shapes.length == 0) {
            throw new IllegalArgumentException("Array is empty");
        }

        final int maxAreaFigureIndexCoefficient = 1;
        Arrays.sort(shapes, new AreaComparator());

        return shapes[shapes.length - maxAreaFigureIndexCoefficient];
    }

    public static Shape getSecondPerimeterShape(Shape[] shapes) {
        if (shapes.length == 0) {
            throw new IllegalArgumentException("Array is empty");
        }

        final int secondPerimeterFigureCoefficient = 2;
        Arrays.sort(shapes, new PerimeterComparator());

        return shapes[shapes.length - secondPerimeterFigureCoefficient];
    }
}
