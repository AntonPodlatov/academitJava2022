package ru.academit.podlatov.minesweeper.model;

public class Cell {
    private final int columnNumber;
    private final int rowNumber;
    private int minesCountAround;
    private boolean isClosed;
    private boolean isMine;

    public Cell(int rowNumber, int columnNumber) {
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
        isMine = false;
        isClosed = true;
    }

    public boolean isMine() {
        return isMine;
    }

    public void makeMine() {
        isMine = true;
        minesCountAround = 9;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void open() {
        isClosed = false;
    }

    public void setMinesCountAround(int minesCountAround) {
        this.minesCountAround = minesCountAround;
    }

    public int getMinesCountAround() {
        return minesCountAround;
    }

    @Override
    public String toString() {
        return "Cell{" +
                ", rowNumber=" + rowNumber +
                ", columnNumber=" + columnNumber +
                ", minesCountAround=" + minesCountAround +
                ", isClosed=" + isClosed +
                ", isMine=" + isMine +
                '}';
    }
}