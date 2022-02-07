package ru.academit.podlatov.matrix;

import ru.academit.podlatov.vector.Vector;

public class Matrix {
    private Vector[] rows;

    public Matrix(int rowsCount, int columnsCount) {
        if (rowsCount <= 0) {
            throw new IllegalArgumentException("RowsCount = " + rowsCount + ". Matrix rowsCount cant be <= 0.");
        }
        if (columnsCount <= 0) {
            throw new IllegalArgumentException("ColumnsCount = " + columnsCount + ". Matrix columnsCount cant be <= 0.");
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
            throw new IllegalArgumentException("Array length = " + array.length + ". Matrix rows count cant be < 1.");
        }

        int maxArraySize = 0;

        for (double[] e : array) {
            if (e.length > maxArraySize) {
                maxArraySize = e.length;
            }
        }

        if (maxArraySize == 0) {
            throw new IllegalArgumentException("Second level array length = " + array[0].length + ". Matrix columns count cant be < 1.");
        }

        rows = new Vector[array.length];

        for (int i = 0; i < rows.length; i++) {
            rows[i] = new Vector(array[i], maxArraySize);
        }
    }

    public Matrix(Vector[] vectors) {
        if (vectors.length == 0) {
            throw new IllegalArgumentException("Array length = " + vectors.length + " Matrix rows count cant be < 1.");
        }

        rows = new Vector[vectors.length];
        int maxVectorSize = 0;

        for (Vector v : vectors) {
            if (v.getSize() > maxVectorSize) {
                maxVectorSize = v.getSize();
            }
        }

        if (maxVectorSize == 0) {
            throw new IllegalArgumentException("Max vector size in vectors = 0. Matrix columns count cant be < 1.");
        }

        for (int i = 0; i < rows.length; i++) {
            rows[i] = new Vector(maxVectorSize);

            for (int j = 0; j < vectors[i].getSize(); j++) {
                rows[i].setElement(j, vectors[i].getElement(j));
            }
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
            throw new IndexOutOfBoundsException("Index = " + index + ". Valid range={0; " + (rows.length - 1) + "}");
        }

        return new Vector(rows[index]);
    }

    public void setRow(int index, Vector vector) {
        if (index < 0 || index >= rows.length) {
            throw new IndexOutOfBoundsException("Index = " + index + ". Valid range={0; " + (rows.length - 1) + "}");
        }
        if (vector.getSize() != getColumnsCount()) {
            throw new IllegalArgumentException("Vector size not match the matrix columns count. Vector size = " + vector.getSize()
                    + ". Matrix columns count = " + getColumnsCount() + ".");
        }

        rows[index] = new Vector(vector);
    }

    public Vector getColumn(int index) {
        if (index < 0 || index >= rows.length) {
            throw new IndexOutOfBoundsException("Index = " + index + ". Valid range={0; " + (rows.length - 1) + "}");
        }

        double[] column = new double[rows.length];

        for (int i = 0; i < rows.length; i++) {
            column[i] = rows[i].getElement(index);
        }

        return new Vector(column);
    }

    public void transpose() {
        Vector[] vectors = new Vector[getColumnsCount()];

        for (int i = 0; i < vectors.length; i++) {
            vectors[i] = new Vector(getColumn(i));
        }

        rows = vectors;
    }

    public void multiplyByScalar(double scalar) {
        for (Vector row : rows) {
            row.multiplyByScalar(scalar);
        }
    }

    public Vector multiplyByVector(Vector vector) {
        if (vector.getSize() != getColumnsCount()) {
            throw new IllegalArgumentException("Column vector length != matrix columns count. Vector size = " + vector.getSize() + ", matrix columns count = " + getColumnsCount() + ".");
        }

        Vector result = new Vector(rows.length);

        for (int i = 0; i < rows.length; i++) {
            result.setElement(i, Vector.getScalarProduct(rows[i], vector));
        }

        return result;
    }

    private void checkSizes(Matrix matrix) {
        if (matrix.getColumnsCount() != getColumnsCount() || matrix.getRowsCount() != getRowsCount()) {
            throw new IllegalArgumentException(
                    "This matrix rows count = " + getRowsCount() +
                            ", this matrix columns count = " + getColumnsCount() +
                            ". Second matrix rows count = " + matrix.getRowsCount() +
                            ", second matrix columns count = " + matrix.getColumnsCount() +
                            ". Matrices must be the same size.");
        }
    }

    public void add(Matrix matrix) {
        checkSizes(matrix);

        for (int i = 0; i < rows.length; i++) {
            rows[i].add(matrix.rows[i]);
        }
    }

    public void subtract(Matrix matrix) {
        checkSizes(matrix);

        for (int i = 0; i < rows.length; i++) {
            rows[i].subtract(matrix.rows[i]);
        }
    }

    private void swapRows(int rowIndex1, int rowIndex2) {
        if (rowIndex1 < 0 || rowIndex1 >= rows.length) {
            throw new IndexOutOfBoundsException("rowIndex1 = " + rowIndex1 + ". Valid range={0; " + (rows.length - 1) + "}");
        }
        if (rowIndex2 < 0 || rowIndex2 >= rows.length) {
            throw new IndexOutOfBoundsException("rowIndex2 = " + rowIndex2 + ". Valid range={0; " + (rows.length - 1) + "}");
        }

        Vector row1 = new Vector(rows[rowIndex1]);
        rows[rowIndex1] = new Vector(rows[rowIndex2]);
        rows[rowIndex2] = row1;
    }

    private void triangulate() {
        for (int i = 0; i < getRowsCount(); i++) {
            double epsilon = 1.0e-10;

            if (Math.abs(rows[i].getElement(i)) <= epsilon) {
                for (int j = 0; j < getRowsCount(); j++) {
                    if (Math.abs(rows[j].getElement(i)) > epsilon) {
                        swapRows(i, j);
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
        if (rows.length != getColumnsCount()) {
            throw new UnsupportedOperationException("Matrix is not quadratic. Rows count = " + getRowsCount() + ". Columns count = " + getColumnsCount());
        }

        Matrix triangularMatrix = new Matrix(this);
        triangularMatrix.triangulate();
        double determinant = triangularMatrix.rows[0].getElement(0);

        for (int i = 1; i < triangularMatrix.rows.length; i++) {
            determinant *= triangularMatrix.rows[i].getElement(i);
        }

        return determinant;
    }

    public static Matrix getDifference(Matrix minuendMatrix, Matrix subtractedMatrix) {
        minuendMatrix.checkSizes(subtractedMatrix);

        Matrix result = new Matrix(minuendMatrix);
        result.subtract(subtractedMatrix);

        return result;
    }

    public static Matrix getSum(Matrix matrix1, Matrix matrix2) {
        matrix1.checkSizes(matrix2);

        Matrix result = new Matrix(matrix1);
        result.add(matrix2);

        return result;
    }

    public static Matrix getProduct(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getColumnsCount() != matrix2.getRowsCount()) {
            throw new IllegalArgumentException("Matrix1 columns count = " + matrix1.getColumnsCount() + ", matrix2 rows count = " + matrix2.getRowsCount() + ". The matrices are inconsistent.");
        }

        Matrix result = new Matrix(matrix1.getRowsCount(), matrix2.getColumnsCount());

        for (int i = 0; i < matrix1.getRowsCount(); i++) {
            for (int j = 0; j < matrix2.getColumnsCount(); j++) {
                result.rows[i].setElement(j, Vector.getScalarProduct(matrix1.rows[i], matrix2.getColumn(j)));
            }
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");

        for (Vector v : rows) {
            stringBuilder.append(v).append(", ");
        }

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append("}");

        return stringBuilder.toString();
    }
}