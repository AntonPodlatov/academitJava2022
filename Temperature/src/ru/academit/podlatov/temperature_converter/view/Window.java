package ru.academit.podlatov.temperature_converter.view;

import ru.academit.podlatov.temperature_converter.model.Converter;
import ru.academit.podlatov.temperature_converter.model.scales.Scale;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public record Window(Scale[] scales, Converter converter) {
    public void start() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = getFrame();

            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }

            JTextField inputField = new JTextField();
            inputField.setBorder(new TitledBorder("Input value"));

            JComboBox<Scale> fromBox = new JComboBox<>(scales);
            JComboBox<Scale> toBox = new JComboBox<>(scales);
            fromBox.setBorder(new TitledBorder("From"));
            toBox.setBorder(new TitledBorder("To"));

            JTextField outputField = new JTextField();
            outputField.setBorder(new TitledBorder("Result"));
            outputField.setEditable(false);

            JButton convertButton = new JButton("Convert");
            convertButton.setBorderPainted(true);
            convertButton.setFocusPainted(false);

            convertButton.addActionListener(e -> {
                Scale fromScale = (Scale) fromBox.getSelectedItem();
                Scale toScale = (Scale) toBox.getSelectedItem();

                if (inputField.getText().isEmpty() || fromScale == null || toScale == null) {
                    JOptionPane.showMessageDialog(frame, "Specify all inputs.", "Error", JOptionPane.INFORMATION_MESSAGE);
                } else if (inputField.getText().contains(",")) {
                    JOptionPane.showMessageDialog(frame, "Wrong number format.\nUse a dot instead of a comma as the decimal separator.",
                            "Error", JOptionPane.INFORMATION_MESSAGE);
                    inputField.setText("");
                } else {
                    try {
                        double numberFromTextField = Double.parseDouble(inputField.getText());
                        double result = converter.convert(fromScale, toScale, numberFromTextField);
                        String roundResult = String.format("%.2f", result);

                        outputField.setText(roundResult);
                    } catch (NumberFormatException exception) {
                        JOptionPane.showMessageDialog(frame, "Not a number.", "Error", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            });

            frame.add(inputField);
            frame.add(fromBox);
            frame.add(toBox);
            frame.add(convertButton);
            frame.add(outputField);
            frame.setVisible(true);
        });
    }

    private JFrame getFrame() {
        JFrame frame = new JFrame("Temperature converter");

        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        int defaultWidth = 435;
        int defaultHeight = 350;
        frame.setBounds(screenDimension.width / 2 - defaultWidth / 2, screenDimension.height / 2 - defaultHeight / 2, defaultWidth, defaultHeight);

        Dimension minDimension = new Dimension(defaultWidth / 2, (int) (defaultHeight * 0.8));
        frame.setMinimumSize(minDimension);

        frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GridLayout gridLayout = new GridLayout();
        gridLayout.setColumns(1);
        gridLayout.setRows(5);
        frame.setLayout(gridLayout);

        return frame;
    }
}