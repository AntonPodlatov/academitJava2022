package ru.academit.podlatov.shapes;

import ru.academit.podlatov.shapeInterface.Shape;

public class Rectangle implements Shape {
    private double height;
    private double width;

    public Rectangle(double height, double width) {
        checkIfANumberIsGreaterThanZero(height);
        checkIfANumberIsGreaterThanZero(width);
        this.height = height;
        this.width = width;
    }

    public void setHeight(double height) {
        checkIfANumberIsGreaterThanZero(height);
        this.height = height;
    }

    public void setWidth(double width) {
        checkIfANumberIsGreaterThanZero(width);
        this.width = width;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public double getArea() {
        return height * width;
    }

    private void checkIfANumberIsGreaterThanZero(double number) {
        final double epsilon = 1.0e-10;
        if (number < -epsilon) {
            throw new IllegalArgumentException("The figure dimension cannot be less than or equal to zero.");
        }
    }

    @Override
    public double getPerimeter() {
        int dimensionsCount = 2;
        return (height * dimensionsCount) + (width * dimensionsCount);
    }

    @Override
    public String toString() {
        return "Rectangle (height= " + height + " , width= " + width + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rectangle rectangle = (Rectangle) o;
        return Double.compare(rectangle.height, height) == 0 && Double.compare(rectangle.width, width) == 0;
    }

    @Override
    public int hashCode() {
        final int prime = 27;
        int hash = 1;
        hash = prime * hash + Double.hashCode(height);
        hash = prime * hash + Double.hashCode(width);
        return hash;
    }
}
