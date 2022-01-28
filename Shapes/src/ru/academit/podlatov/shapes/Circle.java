package ru.academit.podlatov.shapes;

import ru.academit.podlatov.shapes.comparison_functions.CheckIfDoublesIsEqual;
import ru.academit.podlatov.shapes.comparison_functions.CheckIfNumberIsPositive;

public class Circle implements Shape {
    private double radius;

    public Circle(double radius) {
        CheckIfNumberIsPositive.check(radius);
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        CheckIfNumberIsPositive.check(radius);
        this.radius = radius;
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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Circle circle = (Circle) o;
        return CheckIfDoublesIsEqual.isEqual(radius, circle.radius);
    }

    @Override
    public int hashCode() {
        final int prime = 13;
        int hash = 1;
        hash = prime * hash + Double.hashCode(radius);

        return hash;
    }
}
