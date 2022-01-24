package ru.academit.podlatov.shapes_main;

import ru.academit.podlatov.shapeFunctions.ShapeFunctions;
import ru.academit.podlatov.shapeInterface.Shape;
import ru.academit.podlatov.shapes.Circle;
import ru.academit.podlatov.shapes.Rectangle;
import ru.academit.podlatov.shapes.Square;
import ru.academit.podlatov.shapes.Triangle;

public class Main {
    public static void main(String[] args) {
        Square square1 = new Square(12);
        Square square2 = new Square(220);
        Rectangle rectangle1 = new Rectangle(12, 23);
        Circle circle1 = new Circle(233);
        Triangle triangle1 = new Triangle(2, 1, 23, 23, 45, 0);

        Shape[] shapes = new Shape[]{square1, square2, rectangle1, circle1, triangle1};

        Shape maxAreaShape = ShapeFunctions.getMaxAreaFigure(shapes);
        Shape secondPerimeterShape = ShapeFunctions.getSecondPerimeterFigure(shapes);

        System.out.println("Maximum area figure: ");
        System.out.println(maxAreaShape + " area= " + maxAreaShape.getArea());
        System.out.println();
        System.out.println("Second perimeter figure: ");
        System.out.println(secondPerimeterShape + " perimeter= " + secondPerimeterShape.getPerimeter());
    }
}
