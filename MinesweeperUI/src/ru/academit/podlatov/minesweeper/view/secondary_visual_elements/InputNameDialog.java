package ru.academit.podlatov.minesweeper.view.secondary_visual_elements;

import ru.academit.podlatov.minesweeper.model.ScoreRecord;
import ru.academit.podlatov.minesweeper.model.ScoresLoaderAndWriter;
import ru.academit.podlatov.minesweeper.model.GameTimer;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

public class InputNameDialog {
    private final JDialog dialog;
    private final String inputPrompt = "Enter your name here";

    public InputNameDialog(JFrame frame, GameTimer gameTimer) {
        dialog = new JDialog(frame, "Congratulations! You win!", true);

        dialog.setLayout(new GridLayout(2, 1));

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

        dialog.add(nameField);

        JButton saveButton = new JButton("Save");
        dialog.add(saveButton);

        Dimension dimension = new Dimension(240, 130);
        dialog.setBounds(
                frame.getX() + frame.getWidth() / 4,
                frame.getY() + frame.getHeight() / 4,
                dimension.width,
                dimension.height);
        dialog.setMinimumSize(dimension);

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
                    ScoreRecord scoreRecord = new ScoreRecord(score, name);
                    writer.writeScore(scoreRecord);
                } catch (FileNotFoundException e2) {
                    JOptionPane.showMessageDialog(frame, "The file for writing data is not found.", "Error", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(frame, "Writing error.", "Error", JOptionPane.INFORMATION_MESSAGE);
                } catch (URISyntaxException ex) {
                    JOptionPane.showMessageDialog(frame, "Failed to get path to write.", "Error", JOptionPane.INFORMATION_MESSAGE);
                }

                dialog.dispose();
            }
        });
    }

    public void show() {
        dialog.setVisible(true);
    }
}