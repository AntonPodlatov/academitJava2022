package ru.academit.podlatov.minesweeper.model;

import java.util.ArrayList;

public class Model {
    private int minesCount;
    private int sideLength;
    private final Cell[][] matrix;

    private boolean isGameOver;
    private boolean isWin;
    private int openedCount;

    public Model() {
        this(10, 9);
    }

    public Model(int minesCount, int sideLength) {
        this.minesCount = minesCount;
        this.sideLength = sideLength;
        isGameOver = false;
        isWin = false;

        matrix = new Cell[sideLength][sideLength];

        for (int i = 0; i < sideLength; i++) {
            for (int j = 0; j < sideLength; j++) {
                matrix[i][j] = new Cell(i, j);
            }
        }

        layMines(sideLength);
        countAndSetMinesAround(sideLength);

        for (int i = 0; i < sideLength; i++) {
            for (int j = 0; j < sideLength; j++) {
                if (matrix[i][j].isMine()) {
                    System.out.print("mine   ");
                } else {
                    System.out.print(matrix[i][j].getMinesCountAround() + "      ");
                }
            }
            System.out.println();
            System.out.println();
        }
    }

    private void layMines(int sideLength) {
        for (int i = 0; i < minesCount; i++) {
            int locationYAxis = (int) (Math.random() * sideLength);
            int locationXAxis = (int) (Math.random() * sideLength);

            if (!matrix[locationXAxis][locationYAxis].isMine()) {
                matrix[locationXAxis][locationYAxis].makeMine();
            } else {
                i--;
            }
        }
    }

    private void countAndSetMinesAround(int sideLength) {
        for (int x = 0; x < sideLength; x++) {
            for (int y = 0; y < sideLength; y++) {
                if (!matrix[x][y].isMine()) {
                    int bombsCountAround = 0;

                    if (x > 0 && y > 0 && matrix[x - 1][y - 1].isMine()) {
                        bombsCountAround++;
                    }

                    if (y > 0 && matrix[x][y - 1].isMine()) {
                        bombsCountAround++;
                    }

                    if (y < sideLength - 1 && matrix[x][y + 1].isMine()) {
                        bombsCountAround++;
                    }

                    if (x < sideLength - 1 && y > 0 && matrix[x + 1][y - 1].isMine()) {
                        bombsCountAround++;
                    }

                    if (x > 0 && matrix[x - 1][y].isMine()) {
                        bombsCountAround++;
                    }

                    if (x < sideLength - 1 && matrix[x + 1][y].isMine()) {
                        bombsCountAround++;
                    }

                    if (x > 0 && y < sideLength - 1 && matrix[x - 1][y + 1].isMine()) {
                        bombsCountAround++;
                    }

                    if (x < sideLength - 1 && y < sideLength - 1 && matrix[x + 1][y + 1].isMine()) {
                        bombsCountAround++;
                    }
                    matrix[x][y].setMinesCountAround(bombsCountAround);
                }
            }
        }
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

    public Cell getCell(int rowNumber, int columnNumber) {
        if (rowNumber < 0 || columnNumber < 0 || rowNumber >= sideLength || columnNumber >= sideLength) {
            return null;
        }

        return matrix[rowNumber][columnNumber];
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public boolean isWin() {
        return isWin;
    }

    public void clearArea(ArrayList<Integer> forClear) {
        if (forClear.isEmpty()) {
            return;
        }

        int x = forClear.get(0) / 100;
        int y = forClear.get(0) % 100;
        forClear.remove(0);

        if (matrix[x][y].getMinesCountAround() == 0) {
            if (x > 0 && y > 0 && matrix[x - 1][y - 1].isClosed()) {
                matrix[x - 1][y - 1].open();
                openedCount++;

                if (matrix[x - 1][y - 1].getMinesCountAround() == 0) {
                    forClear.add((x - 1) * 100 + (y - 1));
                }
            }
        }

        if (y > 0 && matrix[x][y - 1].isClosed()) {
            matrix[x][y - 1].open();
            openedCount++;

            if (matrix[x][y - 1].getMinesCountAround() == 0) {
                forClear.add(x * 100 + (y - 1));
            }
        }

        if (y < matrix.length - 1 && matrix[x][y + 1].isClosed()) {
            matrix[x][y + 1].open();
            openedCount++;

            if (matrix[x][y + 1].getMinesCountAround() == 0) {
                forClear.add(x * 100 + (y + 1));
            }
        }

        if (x < matrix.length - 1 && y > 0 && matrix[x + 1][y - 1].isClosed()) {
            matrix[x + 1][y - 1].open();
            openedCount++;

            if (matrix[x + 1][y - 1].getMinesCountAround() == 0) {
                forClear.add((x + 1) * 100 + (y - 1));
            }
        }

        if (x > 0 && matrix[x - 1][y].isClosed()) {
            matrix[x - 1][y].open();
            openedCount++;

            if (matrix[x - 1][y].getMinesCountAround() == 0) {
                forClear.add((x - 1) * 100 + y);
            }
        }

        if (x < matrix.length - 1 && matrix[x + 1][y].isClosed()) {
            matrix[x + 1][y].open();
            openedCount++;

            if (matrix[x + 1][y].getMinesCountAround() == 0) {
                forClear.add((x + 1) * 100 + y);
            }
        }

        if (x > 0 && y < matrix.length - 1 && matrix[x - 1][y + 1].isClosed()) {
            matrix[x - 1][y + 1].open();
            openedCount++;

            if (matrix[x - 1][y + 1].getMinesCountAround() == 0) {
                forClear.add((x - 1) * 100 + (y + 1));
            }
        }

        if (x < matrix.length - 1 && y < matrix.length - 1 && matrix[x + 1][y + 1].isClosed()) {
            matrix[x + 1][y + 1].open();
            openedCount++;

            if (matrix[x + 1][y + 1].getMinesCountAround() == 0) {
                forClear.add((x + 1) * 100 + (y + 1));
            }
        }

        clearArea(forClear);
    }

    public void openCell(int x, int y) {
        if (matrix[x][y].isMine()) {
            isGameOver = true;
        } else if (matrix[x][y].getMinesCountAround() == 0) {
            matrix[x][y].open();
            openedCount++;

            ArrayList<Integer> forClear = new ArrayList<>();
            forClear.add(x * 100 + y);

            clearArea(forClear);

            if (openedCount == sideLength * sideLength - minesCount) {
                isWin = true;
            }
        } else {
            matrix[x][y].open();
            openedCount++;

            if (openedCount == sideLength * sideLength - minesCount) {
                isWin = true;
            }
        }
    }

    public void stopGame() {
        isGameOver = true;
    }
}