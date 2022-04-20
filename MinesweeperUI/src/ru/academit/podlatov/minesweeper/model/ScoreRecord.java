package ru.academit.podlatov.minesweeper.model;

import java.io.Serializable;

public record ScoreRecord(int secondsCount, String userName) implements Serializable {
    public int getSecondsCount() {
        return secondsCount;
    }

    public String getUserName() {
        return userName;
    }
}