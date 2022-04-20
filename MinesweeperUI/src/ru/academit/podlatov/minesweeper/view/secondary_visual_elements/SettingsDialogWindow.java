package ru.academit.podlatov.minesweeper.view.secondary_visual_elements;

import ru.academit.podlatov.minesweeper.model.Model;

import javax.swing.*;
import java.awt.*;

public class SettingsDialogWindow {
    private final JDialog jDialog;

    public SettingsDialogWindow(JFrame frame, Model model) {
        jDialog = new JDialog(frame, "settings", true);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));
        jDialog.setLayout(new GridLayout(0, 1));

        JLabel sideLengthLabel = new JLabel(" Field size: ");
        panel.add(sideLengthLabel);
        JTextField sideLengthField = new JTextField();
        panel.add(sideLengthField);

        JLabel minesCountLabel = new JLabel(" Mines count: ");
        panel.add(minesCountLabel);
        JTextField minesCountField = new JTextField();
        panel.add(minesCountField);

        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        jDialog.add(panel);

        JLabel messageLabel = new JLabel(" The settings will be applied at the start of a new game. ");
        jDialog.add(messageLabel);
        JButton saveButton = new JButton("Save");
        jDialog.add(saveButton);

        Dimension dimension = new Dimension(330, 168);
        jDialog.setBounds(frame.getX() + frame.getWidth() / 4, frame.getY() + frame.getHeight() / 4,
                dimension.width,
                dimension.height);
        jDialog.setMinimumSize(dimension);

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
                        jDialog.dispose();
                    }
                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(frame, "Not an integer.", "Error", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    public JDialog getJDialog() {
        return jDialog;
    }
}