package ru.academit.podlatov.minesweeper.listeners;

import ru.academit.podlatov.minesweeper.model.GameFieldModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public record StopGameButtonListener(GameFieldModel fieldModel,
                                     JButton[][] buttons,
                                     JFrame frame) implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int y = 0; y < fieldModel.getMatrix().length; y++) {
            for (int x = 0; x < fieldModel.getMatrix().length; x++) {
                if (buttons[y][x].isEnabled()) {
                    if (fieldModel.getMatrix()[y][x] != GameFieldModel.MINE_VALUE) {
                        buttons[y][x].setText("" + fieldModel.getMatrix()[y][x]);
                    } else {
                        buttons[y][x].setBackground(Color.BLACK);
                        buttons[y][x].setText("B!");
                    }

                    buttons[y][x].setEnabled(false);
                }
            }
        }

        JOptionPane.showMessageDialog(frame, "You Gave Up", "Game Over", JOptionPane.ERROR_MESSAGE);
    }
}