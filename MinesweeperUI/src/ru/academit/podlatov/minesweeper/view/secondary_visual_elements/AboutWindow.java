package ru.academit.podlatov.minesweeper.view.secondary_visual_elements;

import javax.swing.*;
import java.awt.*;

public class AboutWindow {
    private final JDialog dialog;

    public AboutWindow(JFrame frame) {
        dialog = new JDialog(frame, "Minesweeper", true);

        JTextArea textArea = new JTextArea();
        textArea.setText("This is a simple implementation of the"
                + System.lineSeparator()
                + "minesweeper game."
                + System.lineSeparator()
                + "Made using Java Swing.");
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        dialog.add(textArea);
        int sideSize = frame.getHeight() / 2;
        dialog.setMinimumSize(new Dimension(sideSize, sideSize));
        dialog.setBounds(
                frame.getX() + frame.getWidth() / 4,
                frame.getY() + frame.getHeight() / 4,
                sideSize, sideSize);
    }

    public void show() {
        dialog.setVisible(true);
    }
}