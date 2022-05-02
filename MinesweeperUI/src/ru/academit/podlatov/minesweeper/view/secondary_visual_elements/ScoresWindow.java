package ru.academit.podlatov.minesweeper.view.secondary_visual_elements;

import ru.academit.podlatov.minesweeper.model.ScoreRecord;
import ru.academit.podlatov.minesweeper.model.ScoresLoaderAndWriter;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class ScoresWindow {
    private final JDialog dialog;

    public ScoresWindow(JFrame frame) {
        dialog = new JDialog(frame, "Top 10 scores", true);

        String[] columns = {"Position", "Name", "Best time"};
        String[][] data = new String[10][3];

        ScoresLoaderAndWriter scoresLoader = null;

        try {
            scoresLoader = new ScoresLoaderAndWriter();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Data read error.", "Error", JOptionPane.INFORMATION_MESSAGE);
        } catch (URISyntaxException e) {
            JOptionPane.showMessageDialog(frame, "Failed to get path to read data.", "Error", JOptionPane.INFORMATION_MESSAGE);
        }

        assert scoresLoader != null;
        List<ScoreRecord> scores = scoresLoader.getScoresList();

        for (int i = 0; i < scores.size(); i++) {
            ScoreRecord scoreRecord = scores.get(i);
            data[i][0] = String.valueOf(i + 1);
            data[i][1] = scoreRecord.getUserName();

            int secondsTotal = scoreRecord.getSecondsCount();
            final int secondsInHour = 3600;
            final int minutesInHour = 60;
            int hours = secondsTotal / secondsInHour;
            int minutes = (secondsTotal - hours * secondsInHour) / minutesInHour;
            int seconds = secondsTotal - (hours * secondsInHour) - (minutes * minutesInHour);

            data[i][2] = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        }

        JTable table = new JTable(data, columns);
        table.setEnabled(false);
        TableColumn col = table.getColumnModel().getColumn(0);
        col.setMaxWidth(57);

        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        dialog.add(new JScrollPane(table));
        dialog.setBounds(
                frame.getX() + frame.getWidth() / 4,
                frame.getY() + frame.getHeight() / 4,
                frame.getWidth() / 2,
                (int) (frame.getHeight() / 2.1));
    }

    public void show() {
        dialog.setVisible(true);
    }
}