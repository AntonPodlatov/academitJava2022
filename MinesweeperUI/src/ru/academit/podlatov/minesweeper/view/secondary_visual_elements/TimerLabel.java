package ru.academit.podlatov.minesweeper.view.secondary_visual_elements;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.util.concurrent.atomic.AtomicInteger;

public class TimerLabel extends JLabel {
    private AtomicInteger timerValue;
    private final Timer timer;

    public TimerLabel() {
        super("Game time: 00:00");

        timerValue = new AtomicInteger(0);
        timer = new Timer(100, e -> {
            timerValue.getAndIncrement();
            setText(String.format("Game time: %02d:%02d", timerValue.get() / 600, timerValue.get() / 10 % 60));
        });

        setBorder(new BevelBorder(BevelBorder.LOWERED));
    }

    public void startTimer() {
        timer.start();
    }

    public void stopTimer() {
        timer.stop();
    }

    public void resetTimer() {
        setText("Game time: 00:00");
        timer.stop();
        timerValue = new AtomicInteger(0);
    }

    public Integer getTimerValueInSeconds() {
        return timerValue.intValue() / 10;
    }
}