package ru.academit.podlatov.minesweeper.view;

import ru.academit.podlatov.minesweeper.model.Cell;
import ru.academit.podlatov.minesweeper.model.GameTimer;
import ru.academit.podlatov.minesweeper.model.Model;
import ru.academit.podlatov.minesweeper.view.secondary_visual_elements.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class MainWindow {
    private Model model;
    private JButton[][] buttons;
    private Container buttonsGrid;
    private GameTimer gameTimer;
    private JLabel timerLabel;
    private final ImageIcon mineIcon;
    private final ImageIcon flagIcon;
    private final ImageIcon questionIcon;
    private final ImageIcon notMineIcon;
    private final ImageIcon emptyCellIcon;
    private final ImageIcon[] numbersIcons;

    public MainWindow(Model model) {
        this.model = model;

        mineIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("images/mine.png")));
        notMineIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("images/notMine.png")));
        flagIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("images/flag.png")));
        questionIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("images/question.png")));
        emptyCellIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("images/emptyCell.png")));

        numbersIcons = new ImageIcon[8];
        numbersIcons[0] = new ImageIcon(Objects.requireNonNull(getClass().getResource("images/1.png")));
        numbersIcons[1] = new ImageIcon(Objects.requireNonNull(getClass().getResource("images/2.png")));
        numbersIcons[2] = new ImageIcon(Objects.requireNonNull(getClass().getResource("images/3.png")));
        numbersIcons[3] = new ImageIcon(Objects.requireNonNull(getClass().getResource("images/4.png")));
        numbersIcons[4] = new ImageIcon(Objects.requireNonNull(getClass().getResource("images/5.png")));
        numbersIcons[5] = new ImageIcon(Objects.requireNonNull(getClass().getResource("images/6.png")));
        numbersIcons[6] = new ImageIcon(Objects.requireNonNull(getClass().getResource("images/7.png")));
        numbersIcons[7] = new ImageIcon(Objects.requireNonNull(getClass().getResource("images/8.png")));
    }

    public void start() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Minesweeper");
            frame.setLayout(new BorderLayout());

            Dimension minDimension = new Dimension(500, 500);
            frame.setMinimumSize(minDimension);
            setFrameSize(frame);

            JButton newGameButton = new JButton("New game");
            newGameButton.addActionListener(e -> {
                gameTimer.resetTimer();
                timerLabel.setText(gameTimer.getGameTimeString());
                model = new Model(model.getMinesCount(), model.getSideLength());
                frame.remove(buttonsGrid);
                generateCellGrid(frame);
                setFrameSize(frame);
                frame.validate();
            });

            JButton aboutButton = new JButton("About");
            aboutButton.addActionListener(e -> {
                AboutWindow aboutWindow = new AboutWindow(frame);
                aboutWindow.getJDialog().setVisible(true);
            });

            JButton scoresButton = new JButton("High scores");
            scoresButton.addActionListener(e -> {
                ScoresWindow scoresWindow = new ScoresWindow(frame);
                scoresWindow.getJDialog().setVisible(true);
            });

            JButton settingsButton = new JButton("Settings");
            settingsButton.addActionListener(e -> {
                SettingsDialogWindow settingsDialogWindow = new SettingsDialogWindow(frame, model);
                settingsDialogWindow.getJDialog().setVisible(true);
            });

            gameTimer = new GameTimer();
            timerLabel = new JLabel();
            timerLabel.setBorder(new BevelBorder(BevelBorder.LOWERED));
            timerLabel.setText(gameTimer.getGameTimeString());
            gameTimer.addPropertyChangeListener(evt -> timerLabel.setText(gameTimer.getGameTimeString()));

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

                        if (cellButton.isEnabled()) {
                            gameTimer.startTimer();
                        }

                        if (SwingUtilities.isLeftMouseButton(e)) {
                            if (cellButton.isEnabled()) {
                                model.openCell(finalX, finalY);
                                cellButton.setFocusPainted(false);

                                if (model.isWin()) {
                                    gameTimer.stopTimer();
                                    showAllField();
                                    InputNameDialog inputNameDialog = new InputNameDialog(frame, gameTimer);
                                    inputNameDialog.getJDialog().setVisible(true);
                                } else if (model.isGameOver()) {
                                    gameTimer.stopTimer();
                                    showAllField();
                                    JOptionPane.showMessageDialog(frame, "Explosion!", "Game Over", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    synchronize();
                                }
                            }
                        } else if (SwingUtilities.isRightMouseButton(e)) {
                            if (!model.isFlag(finalX, finalY) && !model.isQuestion(finalX, finalY)) {
                                model.setFlag(finalX, finalY);
                            } else if (model.isFlag(finalX, finalY) && !model.isQuestion(finalX, finalY)) {
                                model.removeFlag(finalX, finalY);
                                model.setQuestion(finalX, finalY);
                            } else if (!model.isFlag(finalX, finalY) && model.isQuestion(finalX, finalY)) {
                                model.removeQuestion(finalX, finalY);
                            }
                            synchronize();
                        }
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
                        button.setIcon(numbersIcons[cell.getMinesCountAround() - 1]);
                        button.setDisabledIcon(numbersIcons[cell.getMinesCountAround() - 1]);
                    }

                    if (cell.getMinesCountAround() == 0) {
                        button.setIcon(emptyCellIcon);
                        button.setDisabledIcon(emptyCellIcon);
                    }
                } else {
                    if (cell.isFlagged()) {
                        button.setEnabled(false);
                        button.setIcon(flagIcon);
                        button.setDisabledIcon(flagIcon);
                    } else if (cell.isQuestioned()) {
                        button.setEnabled(false);
                        button.setIcon(questionIcon);
                        button.setDisabledIcon(questionIcon);
                    } else if (!cell.isQuestioned() && !cell.isFlagged()) {
                        button.setEnabled(true);
                        button.setIcon(null);
                        button.setDisabledIcon(null);
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
                Cell cell = model.getCell(y, x);

                if (cell.isFlagged()) {
                    if (cell.isMine()) {
                        if (cell.isQuestioned()) {
                            continue;
                        }

                        button.setIcon(flagIcon);
                        button.setDisabledIcon(flagIcon);

                        cell.open();
                        button.setEnabled(false);
                    } else {
                        button.setIcon(notMineIcon);
                        button.setDisabledIcon(notMineIcon);
                    }
                } else {
                    if (cell.isMine()) {
                        if (cell.isQuestioned()) {
                            continue;
                        }

                        button.setIcon(mineIcon);
                        button.setDisabledIcon(mineIcon);
                        cell.open();
                        button.setEnabled(false);
                    }

                    button.setEnabled(false);
                }
            }
        }
    }
}