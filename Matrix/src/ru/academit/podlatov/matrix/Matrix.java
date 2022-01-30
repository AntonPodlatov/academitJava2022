package ru.academit.podlatov.matrix;

import ru.academit.podlatov.vector.Vector;

public class Matrix {
    private Vector[] rows;

    public Matrix(int rowsCount, int columnsCount) {
        if (rowsCount <= 0) {
            throw new IllegalArgumentException("rowsCount=" + rowsCount + ". Matrix rowsCount cant be <= 0.");
        } else if (columnsCount <= 0) {
            throw new IllegalArgumentException("columnsCount=" + columnsCount + ". Matrix columnsCount cant be <= 0.");
        }

        rows = new Vector[rowsCount];

        for (int i = 0; i < rowsCount; i++) {
            rows[i] = new Vector(columnsCount);
        }
    }

    public Matrix(Matrix matrix) {
        rows = new Vector[matrix.rows.length];

        for (int i = 0; i < rows.length; i++) {
            rows[i] = new Vector(matrix.rows[i]);
        }
    }

    public Matrix(double[][] array) {
        if (array.length == 0) {
            throw new IllegalArgumentException("array length=" + array.length + ". Matrix rows count cant be < 1.");
        }
        if (array[1].length == 0) {
            throw new IllegalArgumentException("second level array length=" + array[1].length + ". Matrix columns count cant be < 1.");
        }

        rows = new Vector[array.length];

        for (int i = 0; i < rows.length; i++) {
            rows[i] = new Vector(array[i]);
        }
    }

    public Matrix(Vector[] vectors) {
        if (vectors.length == 0) {
            throw new IllegalArgumentException("vectors length = " + vectors.length + " Matrix rows count cant be < 1.");
        }

        rows = new Vector[vectors.length];
        int maxArrayLength = 0;

        for (Vector v : vectors) {
            if (v.getSize() > maxArrayLength) {
                maxArrayLength = v.getSize();
            }
        }

        if (maxArrayLength == 0) {
            throw new IllegalArgumentException("Max vector size in vectors  = 0. Matrix columns count cant be < 1.");
        }

        for (int i = 0; i < rows.length; i++) {
            rows[i] = new Vector(vectors[i].getElements(), maxArrayLength);
        }
    }

    public int getRowsCount() {
        return rows.length;
    }

    public int getColumnsCount() {
        return rows[0].getSize();
    }

    public Vector getRow(int index) {
        if (index < 0 || index >= rows.length) {
            throw new IndexOutOfBoundsException("index=" + index + ". Valid range={" + 0 + ";" + (rows.length - 1) + "}");
        }

        return rows[index];
    }

    public void setRow(int index, Vector vector) {
        if (index < 0 || index >= rows.length) {
            throw new IndexOutOfBoundsException("index=" + index + ". Valid range={" + 0 + ";" + (rows.length - 1) + "}");
        }

        rows[index] = vector;
    }

    public Vector getColumnVector(int index) {
        if (index < 0 || index >= rows.length) {
            throw new IndexOutOfBoundsException("index=" + index + ". Valid range={" + 0 + ";" + (rows.length - 1) + "}");
        }

        double[] columnVector = new double[rows.length];

        for (int i = 0; i < rows.length; i++) {
            columnVector[i] = rows[i].getElement(index);
        }

        return new Vector(columnVector);
    }

    public void transpose() {
        if (rows.length != rows[0].getSize()) {
            throw new IllegalArgumentException("Only quadratic matrix can be transposed.");
        }

        Vector[] vectors = new Vector[rows.length];

        for (int i = 0; i < this.getColumnsCount(); i++) {
            vectors[i] = new Vector(this.getColumnVector(i));
        }

        rows = vectors;
    }

    public void multiplyByScalar(double scalar) {
        for (Vector row : rows) {
            row.multiplyByScalar(scalar);
        }
    }

    public Vector multiplyByVector(Vector vector) {
        if (vector.getSize() != this.getColumnsCount()) {
            throw new IllegalArgumentException("Column vector length != matrix columns count");
        }

        Vector result = new Vector(vector.getSize());

        for (int i = 0; i < rows.length; i++) {
            result.setElement(i, Vector.getScalarProduct(rows[i], vector));
        }

        return result;
    }

    public void addMatrix(Matrix matrix) {
        if (matrix.getColumnsCount() != this.getColumnsCount() || matrix.getRowsCount() != this.getRowsCount()) {
            throw new IllegalArgumentException("Added matrices must be the same size.");
        }

        for (int i = 0; i < rows.length; i++) {
            rows[i].add(matrix.rows[i]);
        }
    }

    public void subtractMatrix(Matrix subtracted) {
        if (subtracted.getColumnsCount() != this.getColumnsCount() || subtracted.getRowsCount() != this.getRowsCount()) {
            throw new IllegalArgumentException("Minuend and subtracted matrices must be the same size.");
        }

        for (int i = 0; i < rows.length; i++) {
            rows[i].subtract(subtracted.rows[i]);
        }
    }

    private void swapRows(int rowIndex1, int rowIndex2) {
        if (rowIndex1 < 0 || rowIndex1 >= rows.length) {
            throw new IndexOutOfBoundsException("index=" + rowIndex1 + ". Valid range={" + 0 + ";" + (rows.length - 1) + "}");
        } else if (rowIndex2 < 0 || rowIndex2 >= rows.length) {
            throw new IndexOutOfBoundsException("index=" + rowIndex2 + ". Valid range={" + 0 + ";" + (rows.length - 1) + "}");
        }

        Vector row1 = new Vector(rows[rowIndex1]);
        rows[rowIndex1] = new Vector(rows[rowIndex2]);
        rows[rowIndex2] = row1;
    }

    private void triangulate() {
        for (int i = 0; i < this.getRowsCount(); i++) {
            double epsilon = 1.0e-10;

            if (Math.abs(rows[i].getElement(i)) <= epsilon) {

                for (int j = 0; j < this.getRowsCount(); j++) {

                    if (Math.abs(rows[j].getElement(i)) > epsilon) {
                        this.swapRows(i, j);
                        rows[i].multiplyByScalar(-1);

                        break;
                    }
                }
            }

            for (int j = i; j < rows.length - 1; j++) {
                Vector temp = new Vector(rows[i]);

                if (temp.getElement(i) == 0) {
                    continue;
                }

                temp.multiplyByScalar(rows[j + 1].getElement(i) / temp.getElement(i)); /// temp.getElement(i)
                rows[j + 1].subtract(temp);
            }
        }
    }

    public double getDeterminant() {
        if (rows.length != this.getColumnsCount()) {
            throw new IllegalCallerException("Matrix is not quadratic.");
        }

        Matrix copy = new Matrix(this);
        copy.triangulate();
        double determinant = copy.rows[0].getElement(0);

        for (int i = 1; i < copy.rows.length; i++) {
            determinant *= copy.rows[i].getElement(i);
        }
        return determinant;
    }

    public static Matrix getDifference(Matrix minuendMatrix, Matrix subtractedMatrix) {
        Matrix residualMatrix = new Matrix(minuendMatrix);
        residualMatrix.subtractMatrix(subtractedMatrix);

        return residualMatrix;
    }

    public static Matrix getSumMatrix(Matrix matrix1, Matrix matrix2) {
        Matrix sum = new Matrix(matrix1);
        sum.addMatrix(matrix2);

        return sum;
    }

    public static Matrix multiply(Matrix matrixA, Matrix matrixB) {
        if (matrixA.getColumnsCount() != matrixB.getRowsCount()) {
            throw new IllegalArgumentException("The matrices are inconsistent.");
        }

        Matrix resultMatrix = new Matrix(matrixA.getColumnsCount(), matrixB.getRowsCount());

        for (int i = 0; i < matrixA.getRowsCount(); i++) {
            Vector vector = new Vector(matrixA.getColumnsCount());

            for (int j = 0; j < matrixB.getColumnsCount(); j++) {
                double product = Vector.getScalarProduct(matrixA.getRow(i), matrixB.getColumnVector(j));
                vector.setElement(j, product);
            }
            resultMatrix.setRow(i, vector);
        }

        return resultMatrix;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");
        int i = 0;

        for (Vector v : rows) {
            stringBuilder.append(v.toString());
            i++;
            if (i < rows.length) {
                stringBuilder.append(", ");
            }
        }

        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}