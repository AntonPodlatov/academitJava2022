package ru.academit.podlatov.minesweeper.model;

import java.util.ArrayList;
import java.util.Random;

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
                matrix[i][j] = new Cell();
            }
        }

        layMines(sideLength);
        countAndSetMinesCountAroundMines(sideLength);

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
        return isWin;
    }

    public void openAllZerosAndNeighboringOtherNumbers(ArrayList<Integer> cellsForOpening, ArrayList<Integer> visitedCells) {
        if (cellsForOpening.isEmpty()) {
            return;
        }

        int x = cellsForOpening.get(0) / 100;
        int y = cellsForOpening.get(0) % 100;
        Integer lastRemoved = cellsForOpening.remove(0);
        visitedCells.add(lastRemoved);

        if (x > 0 && y > 0 && matrix[x - 1][y - 1].isClosed()) {
            if (!matrix[x - 1][y - 1].isFlagged() && !matrix[x - 1][y - 1].isQuestioned()) {
                matrix[x - 1][y - 1].open();
                openedCount++;
            }

            if (matrix[x - 1][y - 1].getMinesCountAround() == 0) {
                int cellLocation = (x - 1) * 100 + (y - 1);

                if (!cellsForOpening.contains(cellLocation)) {
                    if (!visitedCells.contains(cellLocation)) {
                        cellsForOpening.add(cellLocation);
                    }
                }
            }
        }

        if (y > 0 && matrix[x][y - 1].isClosed()) {
            if (!matrix[x][y - 1].isFlagged() && !matrix[x][y - 1].isQuestioned()) {
                matrix[x][y - 1].open();
                openedCount++;
            }

            if (matrix[x][y - 1].getMinesCountAround() == 0) {
                int cellLocation = x * 100 + (y - 1);

                if (!cellsForOpening.contains(cellLocation)) {
                    if (!visitedCells.contains(cellLocation)) {
                        cellsForOpening.add(cellLocation);
                    }
                }
            }
        }

        if (y < sideLength - 1 && matrix[x][y + 1].isClosed()) {
            if (!matrix[x][y + 1].isFlagged() && !matrix[x][y + 1].isQuestioned()) {
                matrix[x][y + 1].open();
                openedCount++;
            }

            if (matrix[x][y + 1].getMinesCountAround() == 0) {
                int cellLocation = x * 100 + (y + 1);

                if (!cellsForOpening.contains(cellLocation)) {
                    if (!visitedCells.contains(cellLocation)) {
                        cellsForOpening.add(cellLocation);
                    }
                }
            }
        }

        if (x < sideLength - 1 && y > 0 && matrix[x + 1][y - 1].isClosed()) {
            if (!matrix[x + 1][y - 1].isFlagged() && !matrix[x + 1][y - 1].isQuestioned()) {
                matrix[x + 1][y - 1].open();
                openedCount++;
            }

            if (matrix[x + 1][y - 1].getMinesCountAround() == 0) {
                int cellLocation = (x + 1) * 100 + (y - 1);

                if (!cellsForOpening.contains(cellLocation)) {
                    if (!visitedCells.contains(cellLocation)) {
                        cellsForOpening.add(cellLocation);
                    }
                }
            }
        }

        if (x > 0 && matrix[x - 1][y].isClosed()) {
            if (!matrix[x - 1][y].isFlagged() && !matrix[x - 1][y].isQuestioned()) {
                matrix[x - 1][y].open();
                openedCount++;
            }

            if (matrix[x - 1][y].getMinesCountAround() == 0) {
                int cellLocation = (x - 1) * 100 + y;

                if (!cellsForOpening.contains(cellLocation)) {
                    if (!visitedCells.contains(cellLocation)) {
                        cellsForOpening.add(cellLocation);
                    }
                }
            }
        }

        if (x < sideLength - 1 && matrix[x + 1][y].isClosed()) {
            if (!matrix[x + 1][y].isFlagged() && !matrix[x + 1][y].isQuestioned()) {
                matrix[x + 1][y].open();
                openedCount++;
            }

            if (matrix[x + 1][y].getMinesCountAround() == 0) {
                int cellLocation = (x + 1) * 100 + y;

                if (!cellsForOpening.contains(cellLocation)) {
                    if (!visitedCells.contains(cellLocation)) {
                        cellsForOpening.add(cellLocation);
                    }
                }
            }
        }

        if (x > 0 && y < sideLength - 1 && matrix[x - 1][y + 1].isClosed()) {
            if (!matrix[x - 1][y + 1].isFlagged() && !matrix[x - 1][y + 1].isQuestioned()) {
                matrix[x - 1][y + 1].open();
                openedCount++;
            }

            if (matrix[x - 1][y + 1].getMinesCountAround() == 0) {
                int cellLocation = (x - 1) * 100 + (y + 1);

                if (!cellsForOpening.contains(cellLocation)) {
                    if (!visitedCells.contains(cellLocation)) {
                        cellsForOpening.add(cellLocation);
                    }
                }
            }
        }

        if (x < sideLength - 1 && y < sideLength - 1 && matrix[x + 1][y + 1].isClosed()) {
            if (!matrix[x + 1][y + 1].isFlagged() && !matrix[x + 1][y + 1].isQuestioned()) {
                matrix[x + 1][y + 1].open();
                openedCount++;
            }

            if (matrix[x + 1][y + 1].getMinesCountAround() == 0) {
                int cellLocation = (x + 1) * 100 + (y + 1);

                if (!cellsForOpening.contains(cellLocation)) {
                    if (!visitedCells.contains(cellLocation)) {
                        cellsForOpening.add(cellLocation);
                    }
                }
            }
        }

        openAllZerosAndNeighboringOtherNumbers(cellsForOpening, visitedCells);

    }

    public void openCell(int x, int y) {
        if (matrix[x][y].isMine()) {
            isGameOver = true;
        } else if (matrix[x][y].getMinesCountAround() == 0 && !matrix[x][y].isFlagged() && !matrix[x][y].isQuestioned()) {
            matrix[x][y].open();
            openedCount++;

            if (matrix[x][y].getMinesCountAround() == 0) {
                ArrayList<Integer> cellsForOpening = new ArrayList<>();
                cellsForOpening.add(x * 100 + y);

                ArrayList<Integer> visitedCells = new ArrayList<>();
                openAllZerosAndNeighboringOtherNumbers(cellsForOpening, visitedCells);
            }

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

        if (x > 0 && y > 0) {
            cells.add(matrix[x - 1][y - 1]);
        }

        if (y > 0) {
            cells.add(matrix[x][y - 1]);
        }

        if (y < sideLength - 1) {
            cells.add(matrix[x][y + 1]);
        }

        if (x < sideLength - 1 && y > 0) {
            cells.add(matrix[x + 1][y - 1]);
        }

        if (x > 0) {
            cells.add(matrix[x - 1][y]);
        }

        if (x < sideLength - 1) {
            cells.add(matrix[x + 1][y]);
        }

        if (x > 0 && y < sideLength - 1) {
            cells.add(matrix[x - 1][y + 1]);
        }

        if (x < sideLength - 1 && y < sideLength - 1) {
            cells.add(matrix[x + 1][y + 1]);
        }

        return cells;
    }

    public boolean isRightFlagged(ArrayList<Cell> cells) {
        boolean containsMines = false;

        for (Cell cell : cells) {
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

    public boolean isCellsAroundRightFlagged(int x, int y) {
        ArrayList<Cell> neighbouringCells = getNeighbouringCells(x, y);

        return isRightFlagged(neighbouringCells);
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
}