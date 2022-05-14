package ru.academit.podlatov.minesweeper.view;

import ru.academit.podlatov.minesweeper.model.Cell;
import ru.academit.podlatov.minesweeper.model.GameTimer;
import ru.academit.podlatov.minesweeper.model.Model;
import ru.academit.podlatov.minesweeper.view.secondary_visual_elements.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
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
    private final ImageIcon[] numbersIcons;

    public MainWindow(Model model) {
        this.model = model;

        mineIcon = createImageIcon("mine.png");
        notMineIcon = createImageIcon("notMine.png");
        flagIcon = createImageIcon("flag.png");
        questionIcon = createImageIcon("question.png");

        numbersIcons = new ImageIcon[8];
        numbersIcons[0] = createImageIcon("1.png");
        numbersIcons[1] = createImageIcon("2.png");
        numbersIcons[2] = createImageIcon("3.png");
        numbersIcons[3] = createImageIcon("4.png");
        numbersIcons[4] = createImageIcon("5.png");
        numbersIcons[5] = createImageIcon("6.png");
        numbersIcons[6] = createImageIcon("7.png");
        numbersIcons[7] = createImageIcon("8.png");
    }

    private ImageIcon createImageIcon(String fileName) {
        String folderName = "images/";
        return new ImageIcon(Objects.requireNonNull(getClass().getResource(folderName + fileName)));
    }

    private String secondsToString(int secondsCount) {
        return String.format("Game time: %02d:%02d", secondsCount / 60, secondsCount % 60);
    }

    public void start() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Minesweeper");
            frame.setLayout(new BorderLayout());

            Dimension minDimension = new Dimension(500, 500);
            frame.setMinimumSize(minDimension);
            setFrameSize(frame);

            JButton newGameButton = new JButton("New game");
            newGameButton.addActionListener(e -> newGame(frame));

            JButton aboutButton = new JButton("About");
            aboutButton.addActionListener(e -> {
                AboutWindow aboutWindow = new AboutWindow(frame);
                aboutWindow.show();
            });

            JButton scoresButton = new JButton("High scores");
            scoresButton.addActionListener(e -> {
                ScoresWindow scoresWindow = new ScoresWindow(frame, model.getTopResultsCount());
                scoresWindow.show();
            });

            JButton settingsButton = new JButton("Settings");
            settingsButton.addActionListener(e -> {
                SettingsDialogWindow settingsDialogWindow = new SettingsDialogWindow(frame, model);
                settingsDialogWindow.show();
            });

            gameTimer = new GameTimer();
            timerLabel = new JLabel();
            timerLabel.setBorder(new BevelBorder(BevelBorder.LOWERED));
            timerLabel.setText(secondsToString(gameTimer.getTimerValueInSeconds()));
            gameTimer.addPropertyChangeListener(evt -> timerLabel.setText(secondsToString(gameTimer.getTimerValueInSeconds())));

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

    private void newGame(JFrame frame) {
        gameTimer.resetTimer();
        timerLabel.setText(secondsToString(gameTimer.getTimerValueInSeconds()));
        model = new Model(model.getMinesCount(), model.getSideLength());
        frame.remove(buttonsGrid);
        generateCellGrid(frame);
        setFrameSize(frame);
        frame.validate();
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
                    public void mouseReleased(MouseEvent e) {
                        if (cellButton.isEnabled()) {
                            gameTimer.startTimer();
                        }

                        if (SwingUtilities.isLeftMouseButton(e)) {
                            if (cellButton.isEnabled()) {
                                model.openCell(finalX, finalY);
                                cellButton.setFocusPainted(false);

                                if (model.isExplosionOnFirstOpenedCell()) {
                                    newGame(frame);
                                    model.openCell(finalX, finalY);
                                }

                                if (model.isWin()) {
                                    gameTimer.stopTimer();
                                    synchronize();
                                    showAllField();
                                    InputNameDialog inputNameDialog = new InputNameDialog(frame, gameTimer, model.getTopResultsCount());
                                    inputNameDialog.show();
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
                                synchronize();

                                if (model.isWin()) {
                                    gameTimer.stopTimer();
                                    showAllField();
                                    InputNameDialog inputNameDialog = new InputNameDialog(frame, gameTimer, model.getTopResultsCount());
                                    inputNameDialog.show();
                                }
                            } else if (model.isFlag(finalX, finalY) && !model.isQuestion(finalX, finalY)) {
                                model.removeFlag(finalX, finalY);
                                model.setQuestion(finalX, finalY);
                                synchronize();
                            } else if (!model.isFlag(finalX, finalY) && model.isQuestion(finalX, finalY)) {
                                model.removeQuestion(finalX, finalY);
                                synchronize();
                            }
                        }
                    }
                });

                cellButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (SwingUtilities.isMiddleMouseButton(e)) {
                            if (cellButton.isEnabled()) {
                                cellButton.setBorder(new BevelBorder(BevelBorder.LOWERED));
                            }

                            ArrayList<JButton> buttonsAround = getEnabledButtonsAround(finalX, finalY);

                            for (JButton cellButton : buttonsAround) {
                                cellButton.setBorder(new BevelBorder(BevelBorder.LOWERED));
                            }
                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        if (SwingUtilities.isMiddleMouseButton(e)) {
                            if (cellButton.isEnabled()) {
                                cellButton.setBorder(new LineBorder(new Color(128, 128, 128)));
                                cellButton.setBorderPainted(true);
                            }

                            ArrayList<JButton> buttonsAround = getEnabledButtonsAround(finalX, finalY);

                            for (JButton cellButton : buttonsAround) {
                                cellButton.setBorder(new LineBorder(new Color(128, 128, 128)));
                                cellButton.setBorderPainted(true);
                            }

                            if (!cellButton.isEnabled()) {
                                if (model.isCellsAroundRightFlagged(finalX, finalY)) {
                                    model.openCellsAround(finalX, finalY);
                                }
                            }

                            synchronize();
                        }
                    }
                });

                buttons[x][y] = cellButton;
                buttonsGrid.add(cellButton);
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
                        if (cell.getMinesCountAround() == 9) {
                            button.setIcon(flagIcon);
                            button.setDisabledIcon(flagIcon);
                            button.setBackground(new Color(123, 123, 123));
                            button.setEnabled(false);
                        } else {
                            button.setIcon(numbersIcons[cell.getMinesCountAround() - 1]);
                            button.setDisabledIcon(numbersIcons[cell.getMinesCountAround() - 1]);
                            button.setBackground(new Color(123, 123, 123));
                        }

                    }

                    if (cell.getMinesCountAround() == 0) {
                        button.setBackground(new Color(123, 123, 123));
                    }
                } else {
                    if (cell.isFlagged()) {
                        button.setEnabled(false);
                        button.setIcon(flagIcon);
                        button.setDisabledIcon(flagIcon);
                        button.setBackground(new Color(123, 123, 123));
                    } else if (cell.isQuestioned()) {
                        button.setEnabled(false);
                        button.setIcon(questionIcon);
                        button.setDisabledIcon(questionIcon);
                        button.setBackground(new Color(123, 123, 123));
                    } else if (!cell.isQuestioned() && !cell.isFlagged()) {
                        button.setBackground(null);
                        button.setEnabled(true);
                        button.setIcon(null);
                        button.setDisabledIcon(null);
                        button.setText("");
                    }
                }
            }
        }
    }

    private void showAllField() {
        int sideLength = model.getSideLength();

        for (int y = 0; y < sideLength; y++) {
            for (int x = 0; x < sideLength; x++) {
                JButton button = buttons[y][x];
                Cell cell = model.getCell(y, x);
                MouseListener[] listeners = button.getMouseListeners();

                for (MouseListener listener : listeners) {
                    button.removeMouseListener(listener);
                }

                if (cell.isFlagged()) {
                    if (cell.isMine()) {
                        if (cell.isQuestioned()) {
                            continue;
                        }

                        button.setIcon(flagIcon);
                        button.setDisabledIcon(flagIcon);
                        button.setBackground(new Color(123, 123, 123));

                        cell.open();
                        button.setEnabled(false);
                    } else {
                        button.setIcon(notMineIcon);
                        button.setDisabledIcon(notMineIcon);
                        button.setBackground(new Color(123, 123, 123));

                    }
                } else {
                    if (cell.isMine()) {
                        if (cell.isQuestioned()) {
                            continue;
                        }

                        button.setIcon(mineIcon);
                        button.setDisabledIcon(mineIcon);
                        button.setBackground(new Color(123, 123, 123));
                        cell.open();
                        button.setEnabled(false);
                    }

                    button.setEnabled(false);
                }
            }
        }
    }

    private ArrayList<JButton> getEnabledButtonsAround(int x, int y) {
        ArrayList<JButton> cells = new ArrayList<>();
        int sideLength = buttons.length;

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (j < 0 || j >= sideLength || i < 0 || i >= sideLength) {
                    continue;
                } else if (i == x && j == y) {
                    continue;
                }

                if (buttons[i][j].isEnabled()) {
                    cells.add(buttons[i][j]);
                }
            }
        }

        return cells;
    }
}