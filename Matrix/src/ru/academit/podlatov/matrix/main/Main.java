package ru.academit.podlatov.matrix.main;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] array = new int[10];

        Arrays.fill(array, 23);

        for (int i : array) {
            System.out.println(i);
        }

    }
}
