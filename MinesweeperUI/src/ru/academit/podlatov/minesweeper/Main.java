package ru.academit.podlatov.minesweeper;

import ru.academit.podlatov.minesweeper.model.Model;
import ru.academit.podlatov.minesweeper.view.MainWindow;

public class Main {
    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow(new Model());
        mainWindow.start();
    }
}