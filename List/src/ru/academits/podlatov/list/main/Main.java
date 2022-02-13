package ru.academits.podlatov.list.main;

import ru.academits.podlatov.list.List;

public class Main {
    public static void main(String[] args) {
        List<Character> charList = new List<>();

        charList.insertFirst('d');
        charList.insertFirst('c');
        charList.insertFirst('b');
        charList.insertFirst('a');
        charList.insertFirst(null);

        System.out.println("charList: " + charList);

        charList.revert();
        charList.removeByData(null);

        System.out.println("reverted charList without null: " + charList);
        System.out.println("Second element: " + charList.get(1));

        boolean isDeleted = charList.removeByData('c');
        System.out.println("CharList without 'c' element: " + charList);
        System.out.println("Second element: " + charList.get(1));

        List<Integer> emptyList = new List<>();
        emptyList.revert();
        System.out.println("EmptyList: " + emptyList);
    }
}
