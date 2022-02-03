package ru.academits.podlatov.array_list_home;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ArrayListHome {
    public static void main(String[] args) {
        ArrayList<String> lines = new ArrayList<>();

        try (Scanner scanner = new Scanner(new FileInputStream("ArrayListHome/input.txt"), "windows-1251")) {

            while (scanner.hasNext()) {
                lines.add(scanner.next());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Strings list from \"input.txt\": " + lines);
        System.out.println();

        ArrayList<Integer> intList1 = new ArrayList<>(Arrays.asList(1, 3, 6, 8, 3, 5, 2, 0, 7, 8, 9, 4, -1));
        System.out.println("intList1: " + intList1);

        intList1.removeIf(i -> i % 2 == 0);
        System.out.println("intList1 without even numbers: " + intList1);
        System.out.println();

        ArrayList<Integer> intList2 = new ArrayList<>(Arrays.asList(1, 6, 6, 8, 3, 5, 2, 0, 5, 8, 9, 4, -1, 6, 6));
        System.out.println("List with duplicates: " + intList2);

        ArrayList<Integer> uniqueNumbersList = new ArrayList<>(intList2.size());

        for (Integer i : intList2) {
            if (!uniqueNumbersList.contains(i)) {
                uniqueNumbersList.add(i);
            }
        }

        System.out.println("List without duplicates: " + uniqueNumbersList);
    }
}


