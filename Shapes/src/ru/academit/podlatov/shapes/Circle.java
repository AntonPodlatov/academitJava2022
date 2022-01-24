package ru.academit.podlatov.shapes;

import ru.academit.podlatov.shapeInterface.Shape;

public class Circle implements Shape {
    private double radius;

    public Circle(double radius) {
        checkIfANumberIsGreaterThanZero(radius);
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        checkIfANumberIsGreaterThanZero(radius);
        this.radius = radius;
    }

    private void checkIfANumberIsGreaterThanZero(double number) {
        final double epsilon = 1.0e-10;
        if (number < -epsilon) {
            throw new IllegalArgumentException("The figure dimension cannot be less than or equal to zero.");
        }
    }

    @Override
    public double getWidth() {
        return radius + radius;
    }

    @Override
    public double getHeight() {
        return radius + radius;
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double getPerimeter() {
        return getHeight() * Math.PI;
    }

    @Override
    public String toString() {
        return "Circle (radius= " + radius + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Circle circle = (Circle) o;

        return Double.compare(circle.radius, radius) == 0;
    }

    @Override
    public int hashCode() {
        final int prime = 33;
        int hash = 1;
        hash = prime * hash + Double.hashCode(radius);

        return hash;
    }
}
