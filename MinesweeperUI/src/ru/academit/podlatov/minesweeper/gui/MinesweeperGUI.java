package ru.academit.podlatov.minesweeper.gui;

import ru.academit.podlatov.minesweeper.listeners.*;
import ru.academit.podlatov.minesweeper.model.GameFieldModel;

import javax.swing.*;
import java.awt.*;

public class MinesweeperGUI {
    public void start() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Minesweeper");
            frame.setSize(500, 500);
            frame.setLayout(new BorderLayout());

            Container buttonsGrid = new Container();
            GameFieldModel fieldModel = new GameFieldModel();

            int sideLength = fieldModel.getSideLength();
            buttonsGrid.setLayout(new GridLayout(sideLength, sideLength));
            JButton[][] buttons = new JButton[sideLength][sideLength];

            for (int y = 0; y < buttons.length; y++) {
                for (int x = 0; x < buttons[0].length; x++) {
                    JButton cellButton = new JButton();
                    cellButton.addActionListener(new CellButtonListener(fieldModel, buttons, frame));
                    buttonsGrid.add(buttons[y][x] = cellButton);
                }
            }

            JButton newGameButton = new JButton("new game");
            newGameButton.addActionListener(new NewGameButtonListener(buttons, fieldModel));

            JButton stopGameButton = new JButton("stop game");
            stopGameButton.addActionListener(new StopGameButtonListener(fieldModel, buttons, frame));

            JButton exitButton = new JButton("exit");
            exitButton.addActionListener(new ExitButtonListener(frame));

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(newGameButton);
            buttonPanel.add(stopGameButton);
            buttonPanel.add(exitButton);

            frame.add(buttonPanel, BorderLayout.SOUTH);
            frame.add(buttonsGrid, BorderLayout.CENTER);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}