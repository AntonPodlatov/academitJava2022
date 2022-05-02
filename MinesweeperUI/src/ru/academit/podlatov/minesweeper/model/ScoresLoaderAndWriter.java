package ru.academit.podlatov.minesweeper.model;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ScoresLoaderAndWriter {
    private final String filePath;
    private List<ScoreRecord> scoresList = new ArrayList<>();

    public ScoresLoaderAndWriter() throws IOException, URISyntaxException {
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

    public void trimList() {
        final int size = 10;

        if (scoresList.size() < size) {
            return;
        }

        scoresList = scoresList.subList(0, size);
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
        trimList();
        replaceAllData();
    }

    private String getPath() throws URISyntaxException {
        return  getClass()
                .getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .toURI()
                .getPath();
    }
}