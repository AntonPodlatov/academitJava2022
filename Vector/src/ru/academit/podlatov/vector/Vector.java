package ru.academit.podlatov.vector;

import java.util.Arrays;

public class Vector {
    private double[] elements;

    public Vector(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("size=" + size + ". Vector size must be > 0");
        }

        elements = new double[size];
    }

    public Vector(Vector vector) {
        elements = Arrays.copyOf(vector.elements, vector.elements.length);
    }

    public Vector(double[] array) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Array is empty");
        }

        elements = Arrays.copyOf(array, array.length);
    }

    public Vector(double[] array, int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("size=" + size + ". Vector size must be > 0");
        }
        
        elements = Arrays.copyOf(array, size);
    }

    public int getSize() {
        return elements.length;
    }

    public void add(Vector vector) {
        if (vector.elements.length > elements.length) {
            elements = Arrays.copyOf(elements, vector.elements.length);
        }

        for (int i = 0; i < vector.elements.length; i++) {
            elements[i] += vector.elements[i];
        }
    }

    public void subtract(Vector vector) {
        if (vector.elements.length > elements.length) {
            elements = Arrays.copyOf(elements, vector.elements.length);
        }

        for (int i = 0; i < vector.elements.length; i++) {
            elements[i] -= vector.elements[i];
        }
    }

    public void multiplyByScalar(double scalar) {
        for (int i = 0; i < elements.length; i++) {
            elements[i] *= scalar;
        }
    }

    public void revert() {
        multiplyByScalar(-1);
    }

    public double getLength() {
        double sumOfSquares = 0;

        for (double element : elements) {
            sumOfSquares += element * element;
        }

        return Math.sqrt(sumOfSquares);
    }

    public double getElement(int index) {
        if (index < 0 || index >= elements.length) {
            throw new IndexOutOfBoundsException("index=" + index + ". Valid range={" + 0 + ";" + (elements.length - 1) + "}");
        }

        return elements[index];
    }

    public void setElement(int index, double element) {
        if (index < 0 || index >= elements.length) {
            throw new IndexOutOfBoundsException("index=" + index + ". Valid range={" + 0 + ";" + (elements.length - 1) + "}");
        }

        elements[index] = element;
    }

    public static Vector getSum(Vector vector1, Vector vector2) {
        Vector result = new Vector(vector1);
        result.add(vector2);

        return result;
    }

    public static Vector getDifference(Vector minuend, Vector subtracted) {
        Vector result = new Vector(minuend);
        result.subtract(subtracted);

        return result;
    }

    public static double getScalarProduct(Vector vector1, Vector vector2) {
        Vector maxVector = vector1.getSize() > vector2.getSize() ? vector1 : vector2;
        Vector minVector = vector1.getSize() < vector2.getSize() ? vector1 : vector2;

        double[] array = Arrays.copyOf(minVector.elements, maxVector.getSize());
        double result = 0;
        for (int i = 0; i < array.length; i++) {
            result += array[i] * maxVector.elements[i];
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("{");

        for (int i = 0; i < elements.length; i++) {
            stringBuilder.append(elements[i]);
            if (i < elements.length - 1) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append("}");

        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Vector vector = (Vector) o;

        return Arrays.equals(elements, vector.elements);
    }

    @Override
    public int hashCode() {
        final int prime = 43;
        int hash = 1;

        for (double e : elements) {
            hash = hash * prime + Double.hashCode(e);
        }

        return hash;
    }
}
