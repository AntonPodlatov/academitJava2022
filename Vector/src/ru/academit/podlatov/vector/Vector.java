package ru.academit.podlatov.vector;

import java.util.Arrays;

public class Vector {
    private double[] elements;

    public Vector(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Vector size must be >0");
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

    public Vector(double[] array, int addedElementsCount) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Array is empty");
        } else if (addedElementsCount <= 0) {
            throw new IllegalArgumentException("Vector size must be >0");
        }

        if (array.length > addedElementsCount) {
            elements = Arrays.copyOf(array, addedElementsCount);
        }
        if (array.length == addedElementsCount) {
            elements = Arrays.copyOf(array, array.length);
        }
        if (array.length < addedElementsCount) {
            elements = new double[addedElementsCount];
            System.arraycopy(array, 0, elements, 0, array.length);
        }
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
            elements[i] = elements[i] * scalar;
        }
    }

    public void revert() {
        int vectorReversalFactor = -1;
        for (int i = 0; i < elements.length; i++) {
            elements[i] *= vectorReversalFactor;
        }
    }

    public double getVectorLength() {
        double sumOfSquares = 0;
        for (double element : elements) {
            sumOfSquares += element * element;
        }

        return Math.sqrt(sumOfSquares);
    }

    public double getVectorElement(int index) {
        if (index < 0 || index >= elements.length) {
            throw new IllegalArgumentException("Wrong index.");
        }

        return elements[index];
    }

    public void setVectorElement(double element, int index) {
        if (index < 0 || index >= elements.length) {
            throw new IllegalArgumentException("Wrong index.");
        }

        elements[index] = element;
    }

    public static Vector getVectorsSum(Vector vector1, Vector vector2) {
        Vector result = new Vector(vector1);
        result.add(vector2);

        return result;
    }

    public static Vector getResidualVector(Vector minuend, Vector subtracted) {
        Vector result = new Vector(minuend);
        result.subtract(subtracted);

        return result;
    }

    public static Vector getScalarProductOfVectors(Vector vector1, Vector vector2) {
        Vector maxVector = vector1.getSize() > vector2.getSize() ? vector1 : vector2;
        Vector minVector = vector1.getSize() < vector2.getSize() ? vector1 : vector2;

        double[] array = Arrays.copyOf(minVector.elements,maxVector.getSize());

        for (int i = 0; i < array.length; i++) {
            array[i] *=  maxVector.elements[i];
        }

        return new Vector(array);
    }


    @Override
    public String toString() {
        return "Vector" + Arrays.toString(elements);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;

        return Arrays.equals(elements, vector.elements);
    }

    @Override
    public int hashCode() {
        final int prime = 34;
        int hash = 1;
        for (double d : elements) {
            hash = hash * prime + Double.hashCode(d);
        }

        return hash;
    }
}
