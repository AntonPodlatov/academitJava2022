package ru.academits.podlatov.tree.main;

import ru.academits.podlatov.tree.Tree;

public class Main {
    public static void main(String[] args) {
        Tree<Integer> tree = new Tree<>();
        tree.insert(1);
        tree.insert(0);
        tree.insert(2);
        tree.insert(-1);
        tree.insert(1);
        tree.insert(3);
        tree.insert(-5);
        tree.insert(2);
        tree.insert(-10);
        tree.insert(-2);
        tree.insert(null);
        tree.depthTraversalRecursive();
        System.out.println();
        tree.depthTraversal();
    }
}