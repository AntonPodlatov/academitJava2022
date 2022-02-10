package ru.academits.podlatov.array_list_home;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ArrayListHome {
    public static ArrayList<String> getStringsListFromFile(String inputFilePath) {
        ArrayList<String> lines = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFilePath))) {
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return lines;
    }

    public static void removeEvenNumbers(ArrayList<Integer> numbers) {
        for (int i = 0; i < numbers.size(); i++) {
            if (numbers.get(i) % 2 == 0) {
                numbers.remove(i);
                i--;
            }
        }
    }

    private static ArrayList<Integer> getListWithoutDuplicates(ArrayList<Integer> notUniqueNumbersList) {
        ArrayList<Integer> uniqueNumbersList = new ArrayList<>(notUniqueNumbersList.size());

        for (Integer number : notUniqueNumbersList) {
            if (!uniqueNumbersList.contains(number)) {
                uniqueNumbersList.add(number);
            }
        }

        return uniqueNumbersList;
    }

    public static void main(String[] args) {
        String inputLocation = "ArrayListHome/input.txt";
        System.out.println("Strings list from " + inputLocation + ": " + getStringsListFromFile(inputLocation));
        System.out.println();

        ArrayList<Integer> listWithEvenNumbers = new ArrayList<>(Arrays.asList(1, 3, 6, 8, 3, 5, 2, 0, 7, 8, 9, 4, -1));
        System.out.println("listWithEvenNumbers: " + listWithEvenNumbers);
        removeEvenNumbers(listWithEvenNumbers);
        System.out.println("listWithEvenNumbers without even numbers: " + listWithEvenNumbers);
        System.out.println();

        ArrayList<Integer> integersListWithDuplicates = new ArrayList<>(Arrays.asList(1, 6, 6, 8, 3, 5, 2, 0, 5, 8, 9, 4, -1, 6, 6));
        integersListWithDuplicates.sort(Integer::compare);
        System.out.println("List with duplicates(sorted): " + integersListWithDuplicates);
        System.out.println("List without duplicates: " + getListWithoutDuplicates(integersListWithDuplicates));
    }
}