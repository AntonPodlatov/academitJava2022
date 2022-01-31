package ru.academit.podlatov.shapes;

import ru.academit.podlatov.shapes.comparison_functions.PositivityChecker;

public class Square implements Shape {
    private double sideLength;

    public Square(double sideLength) {
        PositivityChecker.check(sideLength);
        this.sideLength = sideLength;
    }

    public double getSideLength() {
        return sideLength;
    }

    public void setSideLength(double sideLength) {
        PositivityChecker.check(sideLength);
        this.sideLength = sideLength;
    }

    @Override
    public double getWidth() {
        return sideLength;
    }

    @Override
    public double getHeight() {
        return sideLength;
    }

    @Override
    public double getArea() {
        return sideLength * sideLength;
    }

    @Override
    public double getPerimeter() {
        final int sidesCount = 4;
        return sideLength * sidesCount;
    }

    @Override
    public String toString() {
        return "Square (sideLength = " + sideLength + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Square square = (Square) o;
        return square.sideLength == sideLength;
    }

    @Override
    public int hashCode() {
        final int prime = 17;
        int hash = 1;

        hash = hash * prime + Double.hashCode(sideLength);

        return hash;
    }
}
