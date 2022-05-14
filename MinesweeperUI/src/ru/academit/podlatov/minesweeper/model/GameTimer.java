package ru.academit.podlatov.minesweeper.model;

import javax.swing.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.concurrent.atomic.AtomicInteger;

public class GameTimer {
    private AtomicInteger timerValue;
    private final Timer timer;
    private final PropertyChangeSupport propertyChangeSupport;

    public GameTimer() {
        timerValue = new AtomicInteger(0);
        propertyChangeSupport = new PropertyChangeSupport(this);

        timer = new Timer(100, e -> {
            int oldValue = timerValue.intValue();
            timerValue.getAndIncrement();
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
        timer.stop();
        timerValue = new AtomicInteger(0);
    }

    public Integer getTimerValueInSeconds() {
        return timerValue.intValue() / 10;
    }
}