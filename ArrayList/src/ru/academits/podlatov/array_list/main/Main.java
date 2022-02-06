package ru.academits.podlatov.array_list.main;

import ru.academits.podlatov.array_list.ArrayList;

import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> qq = new ArrayList<>();
        qq.add(1);
        qq.add(2);
        qq.add(3);
        qq.add(4);
        qq.add(5);
        qq.add(6);
        qq.add(7);
        qq.add(8);
        System.out.println(qq);

        qq.add(3, 9);
        System.out.println(qq);

        qq.remove(3);
        System.out.println(qq);
        System.out.println(qq.contains(123));

        ArrayList<String> strings = new ArrayList<>();
        strings.add("yyy");

        Iterator<String> iterator = strings.iterator();
        System.out.println(iterator.hasNext());
    }
}
