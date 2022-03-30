package ru.academit.podlatov.minesweeper.view.secondary_visual_elements;

import javax.swing.*;
import java.awt.*;

public class AboutWindow extends JDialog {
    public AboutWindow(JFrame frame) {
        super(frame, "Minesweeper", true);

        JTextArea textField = new JTextArea();
        textField.setText(" This is a simple implementation of the \n minesweeper game.\n Made using Java Swing.");
        textField.setEditable(false);
        textField.setLineWrap(true);
        add(textField);

        int sideSize = frame.getHeight() / 2;
        setMinimumSize(new Dimension(sideSize, sideSize));

        this.setBounds(
                frame.getX() + frame.getWidth() / 4,
                frame.getY() + frame.getHeight() / 4,
                sideSize, sideSize);
    }
}