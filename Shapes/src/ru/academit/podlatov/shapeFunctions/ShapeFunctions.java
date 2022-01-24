package ru.academit.podlatov.shapeFunctions;

import ru.academit.podlatov.shapeComparators.AreaComparator;
import ru.academit.podlatov.shapeComparators.PerimeterComparator;
import ru.academit.podlatov.shapeInterface.Shape;

import java.util.Arrays;

public class ShapeFunctions {
    public static Shape getMaxAreaFigure(Shape[] shapes) {
        final int maxAreaFigureIndexCoefficient = 1;
        Arrays.sort(shapes, new AreaComparator());

        return shapes[shapes.length - maxAreaFigureIndexCoefficient];
    }

    public static Shape getSecondPerimeterFigure(Shape[] shapes) {
        final int secondPerimeterFigureCoefficient = 2;
        Arrays.sort(shapes, new PerimeterComparator());

        return shapes[shapes.length - secondPerimeterFigureCoefficient];
    }
}
