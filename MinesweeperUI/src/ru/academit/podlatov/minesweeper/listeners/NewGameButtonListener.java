package ru.academit.podlatov.minesweeper.listeners;

import ru.academit.podlatov.minesweeper.model.GameFieldModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewGameButtonListener implements ActionListener {
    private final JButton[][] buttons;
    private final int sideLength;
    private int[][] matrix;

    public NewGameButtonListener(JButton[][] buttons, GameFieldModel fieldModel) {
        this.buttons = buttons;
        sideLength = fieldModel.getSideLength();
        matrix = fieldModel.getMatrix();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        for (int x = 0; x < sideLength; x++) {
            for (int y = 0; y < sideLength; y++) {
                buttons[x][y].setEnabled(true);
                buttons[x][y].setText("");
                buttons[x][y].setBackground(UIManager.getColor(1));
            }
        }

        GameFieldModel logicModel = new GameFieldModel();
        matrix = logicModel.getMatrix();
    }
}
