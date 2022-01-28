package ru.academit.podlatov.shapes;

import ru.academit.podlatov.shapes.comparison_functions.CheckIfDoublesIsEqual;
import ru.academit.podlatov.shapes.comparison_functions.CheckIfNumberIsPositive;

public class Rectangle implements Shape {
    private double height;
    private double width;

    public Rectangle(double height, double width) {
        CheckIfNumberIsPositive.check(height);
        CheckIfNumberIsPositive.check(width);
        this.height = height;
        this.width = width;
    }

    public void setHeight(double height) {
        CheckIfNumberIsPositive.check(height);
        this.height = height;
    }

    public void setWidth(double width) {
        CheckIfNumberIsPositive.check(width);
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

    @Override
    public double getPerimeter() {
        final int dimensionsCount = 2;
        return (height * dimensionsCount) + (width * dimensionsCount);
    }

    @Override
    public String toString() {
        return "Rectangle (height= " + height + " , width= " + width + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Rectangle rectangle = (Rectangle) o;
        return CheckIfDoublesIsEqual.isEqual(rectangle.height, height)
                && CheckIfDoublesIsEqual.isEqual(rectangle.width, width);
    }

    @Override
    public int hashCode() {
        final int prime = 23;
        int hash = 1;
        hash = prime * hash + Double.hashCode(height);
        hash = prime * hash + Double.hashCode(width);
        return hash;
    }
}
