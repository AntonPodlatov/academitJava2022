package ru.academit.podlatov.minesweeper.model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameFieldModel {
    private static int[][] matrix;
    private int minesCount;
    private int sideLength;

    public static final int MINE_VALUE = 10;

    public GameFieldModel() {
        this(9, 10);
    }

    public GameFieldModel(int sideLength, int minesCount) {
        this.minesCount = minesCount;
        this.sideLength = sideLength;

        matrix = new int[sideLength][sideLength];

        layMines(sideLength);
        fillCells(sideLength);
    }

    public int getMinesCount() {
        return minesCount;
    }

    public void setMinesCount(int minesCount) {
        this.minesCount = minesCount;
    }

    public int getSideLength() {
        return sideLength;
    }

    public void setSideLength(int sideLength) {
        this.sideLength = sideLength;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    private void fillCells(int sideLength) {
        for (int x = 0; x < sideLength; x++) {
            for (int y = 0; y < sideLength; y++) {
                if (matrix[x][y] != MINE_VALUE) {
                    int bombsCountAround = 0;

                    if (x > 0 && y > 0 && matrix[x - 1][y - 1] == MINE_VALUE) {
                        bombsCountAround++;
                    }

                    if (y > 0 && matrix[x][y - 1] == MINE_VALUE) {
                        bombsCountAround++;
                    }

                    if (y < sideLength - 1 && matrix[x][y + 1] == MINE_VALUE) {
                        bombsCountAround++;
                    }

                    if (x < sideLength - 1 && y > 0 && matrix[x + 1][y - 1] == MINE_VALUE) {
                        bombsCountAround++;
                    }

                    if (x > 0 && matrix[x - 1][y] == MINE_VALUE) {
                        bombsCountAround++;
                    }

                    if (x < sideLength - 1 && matrix[x + 1][y] == MINE_VALUE) {
                        bombsCountAround++;
                    }

                    if (x > 0 && y < sideLength - 1 && matrix[x - 1][y + 1] == MINE_VALUE) {
                        bombsCountAround++;
                    }

                    if (x < sideLength - 1 && y < sideLength - 1 && matrix[x + 1][y + 1] == MINE_VALUE) {
                        bombsCountAround++;
                    }

                    matrix[x][y] = bombsCountAround;
                }
            }
        }
    }

    private void layMines(int sideLength) {
        for (int i = 0; i < minesCount; i++) {
            int locationYAxis = (int) (Math.random() * sideLength);
            int locationXAxis = (int) (Math.random() * sideLength);

            if (matrix[locationXAxis][locationYAxis] == 0) {
                matrix[locationXAxis][locationYAxis] = MINE_VALUE;
            } else {
                i--;
            }
        }
    }

}