package ru.academit.podlatov.minesweeper.view.secondary_visual_elements;

import ru.academit.podlatov.minesweeper.model.ScoreRecord;
import ru.academit.podlatov.minesweeper.model.ScoresLoaderAndWriter;
import ru.academit.podlatov.minesweeper.model.GameTimer;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class InputNameDialog {
    private final JDialog jDialog;
    private final String inputPrompt = "Enter your name here";

    public InputNameDialog(JFrame frame, GameTimer gameTimer) {
        jDialog = new JDialog(frame, "Congratulations! You win!", true);

        jDialog.setLayout(new GridLayout(2, 1));

        JTextField nameField = new JTextField(inputPrompt);
        nameField.setHorizontalAlignment(JTextField.CENTER);
        nameField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                nameField.setBorder(new BevelBorder(BevelBorder.LOWERED));
                nameField.setText("");
            }
        });

        jDialog.add(nameField);

        JButton saveButton = new JButton("Save");
        jDialog.add(saveButton);

        Dimension dimension = new Dimension(240, 130);
        jDialog.setBounds(
                frame.getX() + frame.getWidth() / 4,
                frame.getY() + frame.getHeight() / 4,
                dimension.width,
                dimension.height);
        jDialog.setMinimumSize(dimension);

        saveButton.addActionListener(e -> {
            if (nameField.getText().contains(",")) {
                JOptionPane.showMessageDialog(frame, "A comma is not allowed here.", "Error", JOptionPane.INFORMATION_MESSAGE);
            } else {
                String name;

                if (nameField.getText().equals(inputPrompt) || nameField.getText().isEmpty()) {
                    name = "Anonym";
                } else {
                    name = nameField.getText();
                }

                int score = gameTimer.getTimerValueInSeconds();

                ScoresLoaderAndWriter writer;

                try {
                    writer = new ScoresLoaderAndWriter();
                    ScoreRecord scoreRecord = new ScoreRecord(score,name);
                    writer.writeScore(scoreRecord);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "The file for writing data is not found.", "Error", JOptionPane.INFORMATION_MESSAGE);
                }

                jDialog.dispose();
            }
        });
    }

    public JDialog getJDialog() {
        return jDialog;
    }
}