package ru.academit.podlatov.minesweeper.model;


import java.util.ArrayList;
import java.util.Random;

public class Model {
    private int minesCount;
    private int sideLength;
    private final Cell[][] matrix;

    private boolean isGameOver;
    private int openedCount;

    public Model() {
        this(10, 9);
    }

    public Model(int minesCount, int sideLength) {
        this.minesCount = minesCount;
        this.sideLength = sideLength;
        isGameOver = false;

        matrix = new Cell[sideLength][sideLength];

        for (int i = 0; i < sideLength; i++) {
            for (int j = 0; j < sideLength; j++) {
                matrix[i][j] = new Cell(j, i);
            }
        }

        layMines(sideLength);
        countAndSetMinesCountAroundMines(sideLength);

     /* for (int i = 0; i < sideLength; i++) {
            for (int j = 0; j < sideLength; j++) {
                if (matrix[i][j].isMine()) {
                    System.out.print("mine   ");
                } else {
                    System.out.print(matrix[i][j].getMinesCountAround() + "      ");
                }
            }
            System.out.println();
            System.out.println();
        } */
    }

    private void layMines(int sideLength) {
        Random random = new Random();

        for (int i = 0; i < minesCount; i++) {
            int locationYAxis = random.nextInt(sideLength);
            int locationXAxis = random.nextInt(sideLength);

            if (!matrix[locationXAxis][locationYAxis].isMine()) {
                matrix[locationXAxis][locationYAxis].makeMine();
            } else {
                i--;
            }
        }
    }

    private void countAndSetMinesCountAroundMines(int sideLength) {
        for (int x = 0; x < sideLength; x++) {
            for (int y = 0; y < sideLength; y++) {
                if (!matrix[x][y].isMine()) {
                    int minesCountAround = 0;

                    if (x > 0 && y > 0 && matrix[x - 1][y - 1].isMine()) {
                        minesCountAround++;
                    }

                    if (y > 0 && matrix[x][y - 1].isMine()) {
                        minesCountAround++;
                    }

                    if (y < sideLength - 1 && matrix[x][y + 1].isMine()) {
                        minesCountAround++;
                    }

                    if (x < sideLength - 1 && y > 0 && matrix[x + 1][y - 1].isMine()) {
                        minesCountAround++;
                    }

                    if (x > 0 && matrix[x - 1][y].isMine()) {
                        minesCountAround++;
                    }

                    if (x < sideLength - 1 && matrix[x + 1][y].isMine()) {
                        minesCountAround++;
                    }

                    if (x > 0 && y < sideLength - 1 && matrix[x - 1][y + 1].isMine()) {
                        minesCountAround++;
                    }

                    if (x < sideLength - 1 && y < sideLength - 1 && matrix[x + 1][y + 1].isMine()) {
                        minesCountAround++;
                    }
                    matrix[x][y].setMinesCountAround(minesCountAround);
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
        int foundedMines = countFoundedMines();

        if (minesCount == foundedMines) {
            return true;
        } else return openedCount == sideLength * sideLength - minesCount;
    }

    public void openCell(int x, int y) {
        if (matrix[x][y].isMine()) {
            isGameOver = true;
        } else if (matrix[x][y].getMinesCountAround() == 0 && !matrix[x][y].isFlagged() && !matrix[x][y].isQuestioned()) {
            matrix[x][y].open();
            openedCount++;

            if (matrix[x][y].getMinesCountAround() == 0) {
                openAllZerosAndNeighboringOtherNumbers(x, y);
            }
        } else {
            matrix[x][y].open();
            openedCount++;
        }

    }

    public void setFlag(int x, int y) {
        matrix[x][y].makeFlagged();
    }

    public void setQuestion(int x, int y) {
        matrix[x][y].makeQuestioned();
    }

    public void removeFlag(int x, int y) {
        matrix[x][y].removeFlag();
    }

    public void removeQuestion(int x, int y) {
        matrix[x][y].removeQuestion();
    }

    public boolean isFlag(int x, int y) {
        return matrix[x][y].isFlagged();
    }

    public boolean isQuestion(int x, int y) {
        return matrix[x][y].isQuestioned();
    }

    public ArrayList<Cell> getNeighbouringCells(int x, int y) {
        ArrayList<Cell> cells = new ArrayList<>();

        for (int i = x - 1; i < x + 2; i++) {
            for (int j = y - 1; j < y + 2; j++) {
                if (j < 0 || j >= sideLength || i < 0 || i >= sideLength) {
                    continue;
                } else if (i == x && j == y) {
                    continue;
                }

                cells.add(matrix[i][j]);
            }
        }

        return cells;
    }

    public boolean isCellsAroundRightFlagged(int x, int y) {
        ArrayList<Cell> neighbouringCells = getNeighbouringCells(x, y);

        boolean containsMines = false;

        for (Cell cell : neighbouringCells) {
            if (cell.isMine()) {
                containsMines = true;

                if (!cell.isFlagged()) {
                    return false;
                }
            }

            if (!cell.isMine()) {
                if (cell.isFlagged()) {
                    return false;
                }
            }
        }

        return containsMines;
    }

    public void openCellsAround(int x, int y) {
        ArrayList<Cell> neighbouringCells = getNeighbouringCells(x, y);

        for (Cell cell : neighbouringCells) {
            if (cell.isClosed()) {
                cell.open();
                openedCount++;
            }
        }
    }

    private int countFoundedMines() {
        int foundedMines = 0;

        for (int i = 0; i < sideLength; i++) {
            for (int j = 0; j < sideLength; j++) {
                if (matrix[i][j].isMine()) {
                    if (matrix[i][j].isFlagged() || !matrix[i][j].isClosed()) {
                        foundedMines++;
                    }
                }
            }
        }

        return foundedMines;
    }

    public void openAllZerosAndNeighboringOtherNumbers(int x, int y) {
        ArrayList<Cell> cells = getNeighbouringCells(x, y);

        while (cells.size() > 0) {
            for (int i = 0; i < cells.size(); i++) {
                Cell cell = cells.get(i);

                if (cell.isClosed()) {
                    if (!cell.isFlagged() && !cell.isQuestioned()) {
                        cell.open();
                        cells.remove(cell);
                        openedCount++;

                        if (cell.getMinesCountAround() == 0) {
                            cells.addAll(getNeighbouringCells(cell.getRowNumber(), cell.getColumnNumber()));
                        }
                    }
                } else {
                    cells.remove(cell);
                }
            }
        }
    }
}