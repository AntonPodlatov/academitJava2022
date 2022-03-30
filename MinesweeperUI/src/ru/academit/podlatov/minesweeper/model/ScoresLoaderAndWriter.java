package ru.academit.podlatov.minesweeper.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ScoresLoaderAndWriter {
    private final static String filePath = "MinesweeperUI/src/ru/academit/podlatov/minesweeper/model/scoresData.txt";
    private List<String> list = new ArrayList<>();

    public ScoresLoaderAndWriter() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                list.add(line);
            }

            sortList();

            list.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sortList() {
        list.sort((s1, s2) -> {
            int s1Number = Integer.parseInt(s1, 0, s1.indexOf(","), 10);
            int s2Number = Integer.parseInt(s2, 0, s2.indexOf(","), 10);
            return Integer.compare(s1Number, s2Number);
        });
    }

    public List<String> getList() {
        return list;
    }

    public void trimToSize10() {
        if (list.size() < 10) {
            return;
        }

        list = list.subList(0, 10);
    }

    public String getScoreFromLine(String line) {
        return line.split(",", 0)[0];
    }

    public String getNameFromLine(String line) {
        return line.split(",", 2)[1];
    }

    public void replaceAllData() {
        File file = new File(filePath);
        boolean isDeleted = file.delete();

        try (PrintWriter printWriter = new PrintWriter(filePath)) {
            list.forEach(printWriter::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addToList(String name, String score) {
        list.add(score + "," + name);
    }

    public  void writeScore(String name, String score) {
        addToList(name, score);
        sortList();
        trimToSize10();
        replaceAllData();
    }
}