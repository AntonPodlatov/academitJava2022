package ru.academits.podlatov.hash_table.main;

import ru.academits.podlatov.hash_table.HashTable;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        HashTable<Integer> hashTable = new HashTable<>(5);

        hashTable.addAll(Arrays.asList(1, 2, 3, 4, 5));
        for (Integer i : hashTable) {
            System.out.println(i);
        }
    }
}
