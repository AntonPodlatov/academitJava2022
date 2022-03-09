package ru.academits.podlatov.hash_table.main;

import ru.academits.podlatov.hash_table.HashTable;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        HashTable<Integer> hashTable1 = new HashTable<>(7);
        hashTable1.addAll(Arrays.asList(1, 2, 3, 4, 5, 6));
        hashTable1.remove(5);

        HashTable<Integer> hashTable2 = new HashTable<>(5);
        hashTable2.addAll(Arrays.asList(2, 3, 4, 5));

        System.out.println(hashTable1);
        System.out.println(hashTable2);

        System.out.println(hashTable1.removeAll(hashTable2));
        System.out.println(hashTable1);
        System.out.println();

        HashTable<Integer> hashTable = new HashTable<>(100);
        hashTable.addAll(Arrays.asList(
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
                21, 22, 23, 24, 25, 26, 27, 28, 29, 30));
        System.out.println(hashTable);

        hashTable.remove(23);
        hashTable.add(935467);

        for (Integer e : hashTable) {
            System.out.print(" " + e);
        }
    }
}