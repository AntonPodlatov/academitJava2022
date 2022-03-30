package ru.academit.podlatov.minesweeper.view.secondary_visual_elements;

import ru.academit.podlatov.minesweeper.model.Model;

import javax.swing.*;
import java.awt.*;

public class SettingsDialogWindow extends JDialog {
    JTextField sideLengthField = new JTextField();
    JLabel sideLengthLabel = new JLabel(" Field size: ");

    JTextField minesCountField = new JTextField();
    JLabel minesCountLabel = new JLabel(" Mines count: ");

    JButton saveButton = new JButton("Save");
    JLabel messageLabel = new JLabel(" The settings will be applied at the start of a new game. ");

    public SettingsDialogWindow(JFrame frame, Model model) {
        super(frame, "settings", true);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));
        setLayout(new GridLayout(0, 1));

        panel.add(sideLengthLabel);
        panel.add(sideLengthField);
        panel.add(minesCountLabel);
        panel.add(minesCountField);

        add(panel);
        add(messageLabel);
        add(saveButton);

        this.setBounds(
                frame.getX() + frame.getWidth() / 4,
                frame.getY() + frame.getHeight() / 4,
                (int) (frame.getWidth() / 1.5),
                (int) (frame.getHeight() / 3.3));

        saveButton.addActionListener(e -> {
            if (sideLengthField.getText().isEmpty() || minesCountField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Specify all inputs.", "Error", JOptionPane.INFORMATION_MESSAGE);
                sideLengthField.setText("");
                minesCountField.setText("");

            } else {
                try {
                    int minesCount = Integer.parseInt(minesCountField.getText());
                    int sideLength = Integer.parseInt(sideLengthField.getText());

                    if (minesCount >= sideLength * sideLength / 2) {
                        JOptionPane.showMessageDialog(frame, "Mines count can't be >= half of all cells on field.", "Error", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        model.setMinesCount(minesCount);
                        model.setSideLength(sideLength);
                        this.dispose();
                    }
                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(frame, "Not an integer.", "Error", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }
}