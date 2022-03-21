package ru.academits.podlatov.tree.main;

import ru.academits.podlatov.tree.Tree;

public class Main {
    public static void main(String[] args) {
        Tree<Double> tree1 = new Tree<>();
        tree1.insert(1.0);
        tree1.insert(0.0);
        tree1.insert(2.0);
        tree1.insert(-1.0);
        tree1.insert(1.0);
        tree1.insert(3.0);
        tree1.insert(-5.0);
        tree1.insert(2.9);
        tree1.insert(-10.0);
        tree1.insert(-2.0);
        tree1.insert(null);

        tree1.traverseForBreadth(System.out::println);

        tree1.remove(1.0);
        System.out.println();
        tree1.traverseForBreadth(System.out::println);

        tree1.remove(1.0);
        System.out.println();
        tree1.traverseForBreadth(System.out::println);

        tree1.remove(-5.0);
        tree1.remove(null);
        System.out.println();
        tree1.traverseForBreadth(System.out::println);

        System.out.println();
        Tree<Integer> tree2 = new Tree<>();
        System.out.println(tree2.remove(23));
    }
}