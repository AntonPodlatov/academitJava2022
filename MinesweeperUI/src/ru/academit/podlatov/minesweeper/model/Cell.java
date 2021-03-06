package ru.academit.podlatov.minesweeper.model;

public class Cell {
    private final int columnNumber;
    private final int rowNumber;
    private int minesCountAround;
    private boolean isClosed;
    private boolean isMine;
    private boolean isFlagged;
    private boolean isQuestioned;

    public Cell(int columnNumber, int rowNumber) {
        this.columnNumber = columnNumber;
        this.rowNumber = rowNumber;

        isMine = false;
        isClosed = true;
        isFlagged = false;
        isQuestioned = false;
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

    public void makeFlagged() {
        isFlagged = true;
    }

    public void makeQuestioned() {
        isQuestioned = true;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public boolean isQuestioned() {
        return isQuestioned;
    }

    public void removeFlag() {
        isFlagged = false;
    }

    public void removeQuestion() {
        isQuestioned = false;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public int getRowNumber() {
        return rowNumber;
    }
}