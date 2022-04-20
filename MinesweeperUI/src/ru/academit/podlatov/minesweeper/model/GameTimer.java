package ru.academit.podlatov.minesweeper.model;

import javax.swing.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.concurrent.atomic.AtomicInteger;

public class GameTimer {
    private AtomicInteger timerValue;
    private final Timer timer;
    private String gameTime = "Game time: 00:00";
    private final PropertyChangeSupport propertyChangeSupport;

    public GameTimer() {
        timerValue = new AtomicInteger(0);
        propertyChangeSupport = new PropertyChangeSupport(this);

        timer = new Timer(100, e -> {
            int oldValue = timerValue.intValue();
            timerValue.getAndIncrement();
            gameTime = String.format("Game time: %02d:%02d", timerValue.get() / 600, timerValue.get() / 10 % 60);
            propertyChangeSupport.firePropertyChange("TimerValue", oldValue, timerValue.intValue());
        });
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void startTimer() {
        timer.start();
    }

    public void stopTimer() {
        timer.stop();
    }

    public void resetTimer() {
        gameTime = "Game time: 00:00";
        timer.stop();
        timerValue = new AtomicInteger(0);
    }

    public Integer getTimerValueInSeconds() {
        return timerValue.intValue() / 10;
    }

    public String getGameTimeString() {
        return gameTime;
    }
}