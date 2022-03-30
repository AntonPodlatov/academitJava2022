package ru.academit.podlatov.minesweeper.view.secondary_visual_elements;

import ru.academit.podlatov.minesweeper.model.ScoresLoaderAndWriter;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.util.List;

public class ScoresWindow extends JDialog {
    public ScoresWindow(JFrame frame) {
        super(frame, "Top 10 scores", true);

        String[] columns = {"Position", "Name", "Best time"};
        String[][] data = new String[10][3];

        ScoresLoaderAndWriter scoresLoader = new ScoresLoaderAndWriter();
        List<String> scores = scoresLoader.getList();

        for (int i = 0; i < scores.size(); i++) {
            String line = scores.get(i);
            data[i][0] = String.valueOf(i + 1);
            data[i][1] = scoresLoader.getNameFromLine(line);
            int secondsTotal = Integer.parseInt(scoresLoader.getScoreFromLine(line));
            int hours = secondsTotal / 3600;
            int minutes = (secondsTotal - hours * 3600) / 60;
            int seconds = secondsTotal - (hours * 3600) - (minutes * 60);
            data[i][2] = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        }

        JTable jTable = new JTable(data, columns);
        jTable.setEnabled(false);
        TableColumn col = jTable.getColumnModel().getColumn(0);
        col.setMaxWidth(57);

        add(new JScrollPane(jTable));
        this.setBounds(
                frame.getX() + frame.getWidth() / 4,
                frame.getY() + frame.getHeight() / 4,
                frame.getWidth() / 2,
                (int) (frame.getHeight() / 2.1));
    }
}