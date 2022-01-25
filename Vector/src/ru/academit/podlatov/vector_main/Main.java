package ru.academit.podlatov.vector_main;

import ru.academit.podlatov.vector.Vector;

public class Main {
    public static void main(String[] args) {
        double[] array1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        double[] array2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};


        Vector vector1 = new Vector(array1);
        Vector vector2 = new Vector(array2);
        System.out.println(vector1);
        System.out.println(vector2);
        System.out.println();

        Vector sum = Vector.getVectorsSum(vector1, vector2);
        System.out.println("Sum of vectors: " + sum);

        Vector residue = Vector.getResidualVector(vector2, vector1);
        System.out.println("Residue of vector2 and vector1: " + residue);

        Vector scalarProduct = Vector.getScalarProductOfVectors(vector2, vector1);
        System.out.println("Scalar product of vectors: " + scalarProduct);
    }
}