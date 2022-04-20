package ru.academit.podlatov.minesweeper.view.secondary_visual_elements;

import javax.swing.*;
import java.awt.*;

public class AboutWindow {
   private final JDialog jDialog;

    public AboutWindow(JFrame frame) {
        jDialog = new JDialog(frame, "Minesweeper", true);

        JTextArea jTextArea = new JTextArea();
        jTextArea.setText("This is a simple implementation of the"
                + System.lineSeparator()
                + "minesweeper game."
                + System.lineSeparator()
                + "Made using Java Swing.");
        jTextArea.setEditable(false);
        jTextArea.setLineWrap(true);
        jTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        jDialog.add(jTextArea);
        int sideSize = frame.getHeight() / 2;
        jDialog.setMinimumSize(new Dimension(sideSize, sideSize));
        jDialog.setBounds(
                frame.getX() + frame.getWidth() / 4,
                frame.getY() + frame.getHeight() / 4,
                sideSize, sideSize);
    }

    public JDialog getJDialog() {
        return jDialog;
    }
}