package ru.academit.podlatov.minesweeper.view.secondary_visual_elements;

import ru.academit.podlatov.minesweeper.model.ScoresLoaderAndWriter;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InputNameDialog extends JDialog {
    JTextField nameField = new JTextField("                Enter your name here");
    JButton saveButton = new JButton("Save");

    public InputNameDialog(JFrame frame, TimerLabel timerLabel) {
        super(frame, "Congratulations! You win!", true);

        GridLayout gridLayout = new GridLayout();
        gridLayout.setColumns(1);
        gridLayout.setRows(2);
        setLayout(gridLayout);

        nameField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                nameField.setBorder(new BevelBorder(BevelBorder.LOWERED));
                nameField.setText("");
            }
        });

        add(nameField);
        add(saveButton);

        this.setBounds(
                frame.getX() + frame.getWidth() / 4,
                frame.getY() + frame.getHeight() / 4,
                240,
                130);

        saveButton.addActionListener(e -> {
            if (nameField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "You didn't enter anything.", "Error", JOptionPane.INFORMATION_MESSAGE);
            } else if (nameField.getText().contains(",")) {
                JOptionPane.showMessageDialog(frame, "A comma is not allowed here.", "Error", JOptionPane.INFORMATION_MESSAGE);
            } else {
                String name = nameField.getText();
                int score = timerLabel.getTimerValueInSeconds();
                ScoresLoaderAndWriter writer = new ScoresLoaderAndWriter();
                writer.writeScore(name, String.valueOf(score));
                this.dispose();
            }
        });
    }
}