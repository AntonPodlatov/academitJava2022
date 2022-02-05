package ru.academits.podlatov.list.main;

import ru.academits.podlatov.list.List;

public class Main {
    public static void main(String[] args) {
        List<Character> charList = new List<>();

        charList.insertAtBeginning('d');
        charList.insertAtBeginning('c');
        charList.insertAtBeginning('b');
        charList.insertAtBeginning('a');

        System.out.println("charList: " + charList);

        charList.revert();

        System.out.println("reverted charList: " + charList);
        System.out.println("Second element: " + charList.getData(1));

        boolean isDeleted = charList.removeNodeByData('c');
        System.out.println("CharList without 'c' element: " + charList);
        System.out.println("Second element: " + charList.getData(1));

    }
}
