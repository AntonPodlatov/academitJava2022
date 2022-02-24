package ru.academits.podlatov.array_list.main;

import ru.academits.podlatov.array_list.ArrayList;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);
        integers.add(4);
        integers.add(5);
        integers.add(6);
        integers.add(7);
        integers.add(8);
        integers.add(3, 9);
        integers.add(0, 34);
        integers.add(1, 0);
        integers.add(null);
        integers.addAll(3, Arrays.asList(0, 1, 2, 3, 4));
        System.out.println("Integers: " + integers);

        integers.remove(3);
        System.out.println("Integers, the element at index 3 is removed: " + integers);

        System.out.println("Integers size: " + integers.size());

        System.out.println("First index of 0: " + integers.indexOf(0));
        System.out.println("Last index of 0: " + integers.lastIndexOf(0));
        System.out.println("Last index of null: " + integers.lastIndexOf(null));
        System.out.println(integers.size());
    }
}