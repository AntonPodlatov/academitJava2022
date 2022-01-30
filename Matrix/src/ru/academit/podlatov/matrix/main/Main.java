package ru.academit.podlatov.matrix.main;

import ru.academit.podlatov.matrix.Matrix;

public class Main {
    public static void main(String[] args) {
        Matrix matrix1 = new Matrix(14, 2);
        System.out.println(matrix1);
        System.out.println();

        double[][] doubles = new double[][]{{1, 2, 3, 4}, {3, 5, 1, 1}, {7, 3, 11, 2}, {0, 3, 1, 3}};

        Matrix matrix2 = new Matrix(doubles);
        System.out.println("matrix2 " + matrix2);

        Matrix matrix3 = new Matrix(matrix2);
        matrix3.transpose();

        System.out.println("matrix3 " + matrix3);
        System.out.println();

        Matrix sum = Matrix.getSum(matrix2, matrix3);
        System.out.println("matrix2 and matrix3 sum " + sum);
    }
}
