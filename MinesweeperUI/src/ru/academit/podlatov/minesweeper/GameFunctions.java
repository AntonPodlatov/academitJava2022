package ru.academit.podlatov.minesweeper;

import ru.academit.podlatov.minesweeper.model.GameFieldModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameFunctions {
    public static void clearArea(ArrayList<Integer> forClear, JButton[][] buttons, GameFieldModel fieldModel) {
        if (forClear.isEmpty()) {
            return;
        }

        int[][] matrix = fieldModel.getMatrix();

        int x = forClear.get(0) / 100;
        int y = forClear.get(0) % 100;
        forClear.remove(0);

        if (matrix[x][y] == 0) {
            if (x > 0 && y > 0 && buttons[x - 1][y - 1].isEnabled()) {
                buttons[x - 1][y - 1].setText(matrix[x - 1][y - 1] + "");
                buttons[x - 1][y - 1].setEnabled(false);
                if (matrix[x - 1][y - 1] == 0) {
                    forClear.add((x - 1) * 100 + (y - 1));
                }
            }
        }

        if (y > 0 && buttons[x][y - 1].isEnabled()) {
            buttons[x][y - 1].setText(matrix[x][y - 1] + "");
            buttons[x][y - 1].setEnabled(false);
            if (matrix[x][y - 1] == 0) {
                forClear.add(x * 100 + (y - 1));
            }
        }
        if (y < matrix.length - 1 && buttons[x][y + 1].isEnabled()) {
            buttons[x][y + 1].setText(matrix[x][y + 1] + "");
            buttons[x][y + 1].setEnabled(false);
            if (matrix[x][y + 1] == 0) {
                forClear.add(x * 100 + (y + 1));
            }
        }

        if (x < matrix.length - 1 && y > 0 && buttons[x + 1][y - 1].isEnabled()) {
            buttons[x + 1][y - 1].setText(matrix[x + 1][y - 1] + "");
            buttons[x + 1][y - 1].setEnabled(false);
            if (matrix[x + 1][y - 1] == 0) {
                forClear.add((x + 1) * 100 + (y - 1));
            }
        }

        if (x > 0 && buttons[x - 1][y].isEnabled()) {
            buttons[x - 1][y].setText(matrix[x - 1][y] + "");
            buttons[x - 1][y].setEnabled(false);
            if (matrix[x - 1][y] == 0) {
                forClear.add((x - 1) * 100 + y);
            }
        }

        if (x < matrix.length - 1 && buttons[x + 1][y].isEnabled()) {
            buttons[x + 1][y].setText(matrix[x + 1][y] + "");
            buttons[x + 1][y].setEnabled(false);
            if (matrix[x + 1][y] == 0) {
                forClear.add((x + 1) * 100 + y);
            }
        }

        if (x > 0 && y < matrix.length - 1 && buttons[x - 1][y + 1].isEnabled()) {
            buttons[x - 1][y + 1].setText(matrix[x - 1][y + 1] + "");
            buttons[x - 1][y + 1].setEnabled(false);
            if (matrix[x - 1][y + 1] == 0) {
                forClear.add((x - 1) * 100 + (y + 1));
            }
        }

        if (x < matrix.length - 1 && y < matrix.length - 1 && buttons[x + 1][y + 1].isEnabled()) {
            buttons[x + 1][y + 1].setText(matrix[x + 1][y + 1] + "");
            buttons[x + 1][y + 1].setEnabled(false);
            if (matrix[x + 1][y + 1] == 0) {
                forClear.add((x + 1) * 100 + (y + 1));
            }
        }

        clearArea(forClear, buttons, fieldModel);
    }

    public static void stopGame(JButton[][] buttons, GameFieldModel fieldModel) {
        int[][] matrix = fieldModel.getMatrix();

        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix.length; x++) {
                if (buttons[y][x].isEnabled()) {
                    if (matrix[y][x] != GameFieldModel.MINE_VALUE) {
                        buttons[y][x].setText("" + matrix[y][x]);
                    } else {
                        buttons[y][x].setBackground(Color.BLACK);
                        buttons[y][x].setText("B!");
                    }

                    buttons[y][x].setEnabled(false);
                }
            }
        }
    }

    public static void checkForWon(JButton[][] buttons, GameFieldModel fieldModel) {
        boolean won = true;

        int[][] matrix = fieldModel.getMatrix();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] != GameFieldModel.MINE_VALUE && buttons[i][j].isEnabled()) {
                    won = false;
                }
            }
        }

        if (won) {
            JOptionPane.showMessageDialog(null, "You have won!", "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
