package ru.academit.podlatov.minesweeper.listeners;

import ru.academit.podlatov.minesweeper.GameFunctions;
import ru.academit.podlatov.minesweeper.model.GameFieldModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public record CellButtonListener(GameFieldModel fieldModel,
                                 JButton[][] buttons,
                                 JFrame frame) implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        int sideLength = fieldModel.getSideLength();
        int[][] matrix = fieldModel.getMatrix();

        for (int x = 0; x < sideLength; x++) {
            for (int y = 0; y < sideLength; y++) {
                if (e.getSource() == buttons[x][y]) {
                    if (matrix[x][y] == GameFieldModel.MINE_VALUE) {
                        GameFunctions.stopGame(buttons, fieldModel);
                        JOptionPane.showMessageDialog(frame, "Explosion!", "Game Over", JOptionPane.ERROR_MESSAGE);
                    } else if (matrix[x][y] == 0) {
                        buttons[x][y].setText(matrix[x][y] + "");
                        buttons[x][y].setEnabled(false);

                        ArrayList<Integer> forClear = new ArrayList<>();
                        forClear.add(x * 100 + y);

                        GameFunctions.clearArea(forClear, buttons, fieldModel);
                        GameFunctions.checkForWon(buttons, fieldModel);
                    } else {
                        buttons[x][y].setText("" + matrix[x][y]);
                        buttons[x][y].setEnabled(false);

                        GameFunctions.checkForWon(buttons, fieldModel);
                    }
                }
            }
        }
    }
}