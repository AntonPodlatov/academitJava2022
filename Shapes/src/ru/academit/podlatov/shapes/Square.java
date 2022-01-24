package ru.academit.podlatov.shapes;

import ru.academit.podlatov.shapeInterface.Shape;

public class Square implements Shape {
    private double sideLength;

    public Square(double sideLength) {
        checkIfANumberIsGreaterThanZero(sideLength);
        this.sideLength = sideLength;
    }

    public double getSideLength() {
        return sideLength;
    }

    public void setSideLength(double sideLength) {
        checkIfANumberIsGreaterThanZero(sideLength);
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

    private void checkIfANumberIsGreaterThanZero(double number) {
        final double epsilon = 1.0e-10;
        if (number < -epsilon) {
            throw new IllegalArgumentException("The figure dimension cannot be less than or equal to zero.");
        }
    }

    @Override
    public double getPerimeter() {
        int sidesCount = 4;
        return sideLength * sidesCount;
    }

    @Override
    public String toString() {
        return "Square (sideLength= " + sideLength + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return Double.compare(square.sideLength, sideLength) == 0;
    }

    @Override
    public int hashCode() {
        final int prime = 22;
        int hash = 1;
        hash = hash * prime + Double.hashCode(sideLength);
        return hash;
    }
}
