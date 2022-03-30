package ru.academit.podlatov.minesweeper.view;

import ru.academit.podlatov.minesweeper.model.Cell;
import ru.academit.podlatov.minesweeper.model.Model;
import ru.academit.podlatov.minesweeper.view.secondary_visual_elements.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainWindow {
    private Model model;
    private JButton[][] buttons;
    private Container buttonsGrid;
    private TimerLabel timerLabel;

    public MainWindow(Model model) {
        this.model = model;
    }

    public void start() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Minesweeper");
            frame.setLayout(new BorderLayout());

            Dimension minDimension = new Dimension(500, 500);
            frame.setMinimumSize(minDimension);
            setFrameSize(frame);

            JButton newGameButton = new JButton("new game");
            newGameButton.addActionListener(e -> {
                timerLabel.resetTimer();
                model = new Model(model.getMinesCount(), model.getSideLength());
                frame.remove(buttonsGrid);
                generateCellGrid(frame);
                setFrameSize(frame);
                frame.validate();
            });

            JButton aboutButton = new JButton("about");
            aboutButton.addActionListener(e -> {
                AboutWindow aboutWindow = new AboutWindow(frame);
                aboutWindow.setVisible(true);
            });

            JButton scoresButton = new JButton("high scores");
            scoresButton.addActionListener(e -> {
                ScoresWindow scoresWindow = new ScoresWindow(frame);
                scoresWindow.setVisible(true);
            });

            JButton settingsButton = new JButton("settings");
            settingsButton.addActionListener(e -> {
                SettingsDialogWindow settingsDialogWindow = new SettingsDialogWindow(frame, model);
                settingsDialogWindow.setVisible(true);

            });

            timerLabel = new TimerLabel();

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(newGameButton);
            buttonPanel.add(settingsButton);
            buttonPanel.add(aboutButton);
            buttonPanel.add(scoresButton);
            buttonPanel.add(timerLabel);

            generateCellGrid(frame);

            frame.add(buttonPanel, BorderLayout.SOUTH);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

    private void setFrameSize(JFrame frame) {
        int defaultFrameSize = 500;
        if (model.getSideLength() > 10) {
            int difference = model.getSideLength() - 10;
            difference *= 45;
            defaultFrameSize += difference;
        }

        frame.setSize(defaultFrameSize, (int) (defaultFrameSize * 1.06));
    }

    private void generateCellGrid(JFrame frame) {
        int sideLength = model.getSideLength();
        buttonsGrid = new Container();
        buttonsGrid.setLayout(new GridLayout(sideLength, sideLength));
        buttons = new JButton[sideLength][sideLength];

        for (int x = 0; x < sideLength; x++) {
            for (int y = 0; y < sideLength; y++) {
                JButton cellButton = new JButton();

                int finalY = y;
                int finalX = x;
                cellButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        super.mouseClicked(e);

                        timerLabel.startTimer();
                        model.openCell(finalX, finalY);
                        cellButton.setFocusPainted(false);

                        if (cellButton.isEnabled()){
                            if (model.isWin()) {
                                timerLabel.stopTimer();
                                System.out.println(timerLabel.getTimerValueInSeconds());
                                showAllField();
                                InputNameDialog inputNameDialog = new InputNameDialog(frame, timerLabel);
                                inputNameDialog.setVisible(true);
                            }

                            if (model.isGameOver()) {
                                timerLabel.stopTimer();
                                //System.out.println(timerLabel.getTimerValueInSeconds());
                                showAllField();
                                JOptionPane.showMessageDialog(frame, "Explosion!", "Game Over", JOptionPane.ERROR_MESSAGE);
                            }
                        }

                        synchronize();
                    }
                });

                buttonsGrid.add(buttons[x][y] = cellButton);
            }
        }

        frame.add(buttonsGrid, BorderLayout.CENTER);
    }

    private void synchronize() {
        int sideLength = model.getSideLength();

        for (int i = 0; i < sideLength; i++) {
            for (int j = 0; j < sideLength; j++) {
                Cell cell = model.getCell(i, j);
                JButton button = buttons[i][j];

                if (!cell.isClosed()) {
                    button.setEnabled(false);

                    if (cell.getMinesCountAround() > 0) {
                        button.setText(String.valueOf(cell.getMinesCountAround()));
                    }

                    if (cell.getMinesCountAround() == 0) {
                        button.setText("");
                    }
                }
            }
        }
    }

    public void showAllField() {
        int sideLength = model.getSideLength();

        for (int y = 0; y < sideLength; y++) {
            for (int x = 0; x < sideLength; x++) {
                JButton button = buttons[y][x];

                if (button.isEnabled()) {
                    if (!model.getCell(y, x).isMine()) {
                        Cell cell = model.getCell(y, x);

                        if (cell.getMinesCountAround() > 0) {
                            button.setText(String.valueOf(cell.getMinesCountAround()));
                        }

                        if (cell.getMinesCountAround() == 0) {
                            button.setText("");
                        }
                    } else {
                        buttons[y][x].setIcon(new ImageIcon("MinesweeperAt4/src/ru/academit/podlatov/minesweeper/resources/mine.png"));
                        buttons[y][x].setDisabledIcon(new ImageIcon("MinesweeperAt4/src/ru/academit/podlatov/minesweeper/resources/mine.png"));
                    }

                    buttons[y][x].setEnabled(false);
                }
            }
        }
    }
}