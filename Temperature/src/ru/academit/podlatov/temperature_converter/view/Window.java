package ru.academit.podlatov.temperature_converter.view;

import ru.academit.podlatov.temperature_converter.controller.Converter;
import ru.academit.podlatov.temperature_converter.model.Celsius;
import ru.academit.podlatov.temperature_converter.model.Fahrenheit;
import ru.academit.podlatov.temperature_converter.model.Kelvin;
import ru.academit.podlatov.temperature_converter.model.Scale;
import ru.academit.podlatov.temperature_converter.utils.Utils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class Window {
    public void start() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = getFrame();
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }

            JTextField inputField = new JTextField();
            inputField.setBorder(new TitledBorder("INPUT VALUE"));

            Scale[] scales = {new Celsius(), new Fahrenheit(), new Kelvin()};
            JComboBox<Scale> fromBox = new JComboBox<>(scales);
            JComboBox<Scale> toBox = new JComboBox<>(scales);
            fromBox.setBorder(new TitledBorder("FROM"));
            toBox.setBorder(new TitledBorder("TO"));

            JTextField outputField = new JTextField();
            outputField.setBorder(new TitledBorder("RESULT"));
            outputField.setEditable(false);

            JButton convertButton = new JButton("do conversion");
            convertButton.setBorderPainted(true);
            convertButton.setBorder(new TitledBorder("CONVERT"));
            convertButton.setFocusPainted(false);

            convertButton.addActionListener(e -> {
                Scale fromScale = (Scale) fromBox.getSelectedItem();
                Scale toScale = (Scale) toBox.getSelectedItem();

                if (inputField.getText().isEmpty() || fromScale == null || toScale == null) {
                    JOptionPane.showMessageDialog(frame, "Specify all inputs.", "Error", JOptionPane.INFORMATION_MESSAGE);
                    outputField.setText("");
                } else if (!Utils.isNumeric(inputField.getText())) {
                    JOptionPane.showMessageDialog(frame, "Not a number.", "Error", JOptionPane.INFORMATION_MESSAGE);
                    outputField.setText("");
                } else {
                    Converter converter = new Converter();
                    double result = converter.convert(fromScale, toScale, Double.parseDouble(inputField.getText()));

                    outputField.setText(Double.toString(result));
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
        String name = "Temperature converter";
        final JFrame jFrame = new JFrame(name);
        final Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int defaultWidth = 430;
        int defaultHeight = 350;
        jFrame.setBounds(dimension.width / 2 - defaultWidth / 2, dimension.height / 2 - defaultHeight / 2, defaultWidth, defaultHeight);

        jFrame.setAlwaysOnTop(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GridLayout gridLayout = new GridLayout();
        gridLayout.setColumns(1);
        gridLayout.setRows(5);
        jFrame.setLayout(gridLayout);

        return jFrame;
    }
}