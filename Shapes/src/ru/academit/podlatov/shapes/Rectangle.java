package ru.academit.podlatov.shapes;

import ru.academit.podlatov.shapes.comparison_functions.PositivityChecker;

public class Rectangle implements Shape {
    private double height;
    private double width;

    public Rectangle(double height, double width) {
        PositivityChecker.check(height);
        PositivityChecker.check(width);
        this.height = height;
        this.width = width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        PositivityChecker.check(height);
        this.height = height;
    }

    @Override
    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        PositivityChecker.check(width);
        this.width = width;
    }

    @Override
    public double getArea() {
        return height * width;
    }

    @Override
    public double getPerimeter() {
        final int dimensionsCount = 2;
        return (height + width) * dimensionsCount;
    }

    @Override
    public String toString() {
        return "Rectangle (height = " + height + ", width = " + width + ")";
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
        return rectangle.height == height && rectangle.width == width;
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
