package ru.academit.podlatov.shapes.main;

import ru.academit.podlatov.shapes.Shape;
import ru.academit.podlatov.shapes.Circle;
import ru.academit.podlatov.shapes.Rectangle;
import ru.academit.podlatov.shapes.Square;
import ru.academit.podlatov.shapes.Triangle;

public class Main {
    public static void main(String[] args) {

        Shape[] shapes = {
                new Square(12),
                new Square(220),
                new Rectangle(12, 23),
                new Circle(233),
                new Triangle(2, 1, 23, 23, 45, 0)
        };

        Shape maxAreaShape = ShapeFunctions.getMaxAreaShape(shapes);
        Shape secondPerimeterShape = ShapeFunctions.getSecondPerimeterShape(shapes);

        System.out.println("Maximum area shape: ");
        System.out.println(maxAreaShape + " area= " + maxAreaShape.getArea());
        System.out.println();
        System.out.println("Second perimeter shape: ");
        System.out.println(secondPerimeterShape + " perimeter= " + secondPerimeterShape.getPerimeter());
    }
}
