package ru.academit.podlatov.shapes.main;

import ru.academit.podlatov.shapes.comparators.AreaComparator;
import ru.academit.podlatov.shapes.comparators.PerimeterComparator;
import ru.academit.podlatov.shapes.Shape;

import java.util.Arrays;

public class ShapeFunctions {
    public static Shape getMaxAreaShape(Shape[] shapes) {
        if (shapes.length == 0) {
            throw new IllegalArgumentException("Array is empty.");
        }

        final int maxAreaShapeIndexOffset = 1;
        Arrays.sort(shapes, new AreaComparator());

        return shapes[shapes.length - maxAreaShapeIndexOffset];
    }

    public static Shape getSecondPerimeterShape(Shape[] shapes) {
        if (shapes.length < 2) {
            throw new IllegalArgumentException("Array length = " + shapes.length + ", cant be < 2.");
        }

        final int secondPerimeterShapeIndexOffset = 2;
        Arrays.sort(shapes, new PerimeterComparator());

        return shapes[shapes.length - secondPerimeterShapeIndexOffset];
    }
}
