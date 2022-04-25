package ru.academit.podlatov.minesweeper.model;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ScoresLoaderAndWriter {
    private final String filePath;
    private List<ScoreRecord> scoresList = new ArrayList<>();

    public ScoresLoaderAndWriter() throws IOException {
        filePath = getPath() + "scoresData.bin";
        File file = new File(filePath);

        if (!file.isFile()) {
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();
        }

        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filePath))) {
            ScoreRecord scoreRecord;

            while ((scoreRecord = (ScoreRecord) objectInputStream.readObject()) != null) {
                scoresList.add(scoreRecord);
            }
        } catch (ClassNotFoundException | EOFException ignored) {
        }


    }

    public void sortList() {
        scoresList.sort((s1, s2) -> {
            int s1Number = s1.getSecondsCount();
            int s2Number = s2.getSecondsCount();
            return Integer.compare(s1Number, s2Number);
        });
    }

    public List<ScoreRecord> getScoresList() {
        return scoresList;
    }

    public void trimToSize10() {
        if (scoresList.size() < 10) {
            return;
        }

        scoresList = scoresList.subList(0, 10);
    }

    private void replaceAllData() throws IOException {
        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            for (ScoreRecord scoreRecord : getScoresList()) {
                stream.writeObject(scoreRecord);
            }
        }
    }

    public void addToList(ScoreRecord scoreRecord) {
        scoresList.add(scoreRecord);
    }

    public void writeScore(ScoreRecord scoreRecord) throws IOException {
        addToList(scoreRecord);
        sortList();
        trimToSize10();
        replaceAllData();
    }

    private String getPath() {
        String path = null;

        try {
            path = getClass()
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI()
                    .getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return path;
    }
}