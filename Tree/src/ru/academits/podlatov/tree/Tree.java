package ru.academits.podlatov.tree;

import java.util.*;
import java.util.function.Consumer;

public class Tree<T> {
    private Node<T> root;
    private int size;
    private Comparator<? super T> comparator;

    public Tree() {
    }

    public Tree(Comparator<? super T> comparator) {
        this.comparator = comparator;
    }

    public boolean isEmpty() {
        return root == null;
    }

    private int compare(T data1, T data2) {
        if (comparator != null) {
            return comparator.compare(data1, data2);
        }

        if (data1 == null) {
            if (data2 == null) {
                return 0;
            }

            return -1;
        }

        if (data2 == null) {
            return 1;
        }

        //noinspection unchecked
        return ((Comparable<? super T>) data1).compareTo(data2);
    }

    public int getSize() {
        return size;
    }

    public void insert(T data) {
        if (root == null) {
            root = new Node<>(data);
            size++;
            return;
        }

        Node<T> currentNode = root;

        while (true) {
            if (compare(data, currentNode.getData()) < 0) {
                if (currentNode.getLeft() != null) {
                    currentNode = currentNode.getLeft();
                } else {
                    currentNode.setLeft(new Node<>(data));
                    size++;
                    return;
                }
            } else {
                if (currentNode.getRight() != null) {
                    currentNode = currentNode.getRight();
                } else {
                    currentNode.setRight(new Node<>(data));
                    size++;
                    return;
                }
            }
        }
    }

    public boolean contains(T data) {
        if (root == null) {
            return false;
        }

        Node<T> parentNode = getParentOf(data);

        if (parentNode == null) {
            return compare(data, root.getData()) == 0;
        }

        return true;
    }

    private Node<T> getParentOf(T data) {
        if (isEmpty()) {
            return null;
        }

        Node<T> currentNode = root;
        Node<T> parentNode = null;

        while (true) {
            int comparisonResult = compare(data, currentNode.getData());

            if (comparisonResult == 0) {
                return parentNode;
            }

            if (comparisonResult < 0) {
                if (currentNode.getLeft() != null) {
                    parentNode = currentNode;
                    currentNode = currentNode.getLeft();
                } else {
                    return null;
                }
            } else {
                if (currentNode.getRight() != null) {
                    parentNode = currentNode;
                    currentNode = currentNode.getRight();
                } else {
                    return null;
                }
            }
        }
    }

    public boolean remove(T data) {
        if (isEmpty()) {
            return false;
        }

        Node<T> parentNode = getParentOf(data);

        if (parentNode == null) {
            if (compare(data, root.getData()) == 0) {
                removeRoot();
                size--;
                return true;
            }

            return false;
        }

        boolean isLeft = compare(data, parentNode.getData()) < 0;
        Node<T> removableNode;

        if (isLeft) {
            removableNode = parentNode.getLeft();
        } else {
            removableNode = parentNode.getRight();
        }

        if (removableNode.hasNoChildren()) {
            if (isLeft) {
                parentNode.setLeft(null);
            } else {
                parentNode.setRight(null);
            }

            size--;
            return true;
        }

        if (removableNode.hasOnlyRight()) {
            if (isLeft) {
                parentNode.setLeft(removableNode.getRight());
            } else {
                parentNode.setRight(removableNode.getRight());
            }

            size--;
            return true;
        }

        if (removableNode.hasOnlyLeft()) {
            if (isLeft) {
                parentNode.setLeft(removableNode.getLeft());
            } else {
                parentNode.setRight(removableNode.getLeft());
            }

            size--;
            return true;
        }

        removeNodeWith2Children(removableNode, parentNode);
        size--;

        return true;
    }

    private void removeRoot() {
        if (root.hasNoChildren()) {
            root = null;
            return;
        }

        if (root.hasOnlyLeft()) {
            root = root.getLeft();
            return;
        }

        if (root.hasOnlyRight()) {
            root = root.getRight();
            return;
        }

        removeNodeWith2Children(root, null);
    }

    private void removeNodeWith2Children(Node<T> removableNode, Node<T> removableNodeParent) {
        Node<T> mostLeftParent = removableNode;
        Node<T> mostLeft = removableNode.getRight();

        if (mostLeft.getLeft() != null) {
            while (mostLeft.getLeft() != null) {
                mostLeftParent = mostLeft;
                mostLeft = mostLeft.getLeft();
            }

            mostLeftParent.setLeft(mostLeft.getRight());
            mostLeft.setRight(removableNode.getRight());
        }

        mostLeft.setLeft(removableNode.getLeft());

        if (removableNodeParent == null) {
            root = mostLeft;
            return;
        }

        if (removableNodeParent.getLeft() == removableNode) {
            removableNodeParent.setLeft(mostLeft);
            return;
        }

        removableNodeParent.setRight(mostLeft);
    }

    public void traverseForBreadth(Consumer<T> consumer) {
        if (isEmpty()) {
            return;
        }

        Queue<Node<T>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node<T> currentNode = queue.remove();
            consumer.accept(currentNode.getData());

            if (currentNode.getLeft() != null) {
                queue.add(currentNode.getLeft());
            }

            if (currentNode.getRight() != null) {
                queue.add(currentNode.getRight());
            }
        }
    }

    private void visit(Node<T> node, Consumer<T> consumer) {
        consumer.accept(node.getData());

        if (node.getLeft() != null) {
            visit(node.getLeft(), consumer);
        }

        if (node.getRight() != null) {
            visit(node.getRight(), consumer);
        }
    }

    public void traverseForDepthRecursive(Consumer<T> consumer) {
        if (isEmpty()) {
            return;
        }

        visit(root, consumer);
    }

    public void traverseForDepth(Consumer<T> consumer) {
        if (isEmpty()) {
            return;
        }

        Deque<Node<T>> stack = new LinkedList<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            Node<T> currentNode = stack.pop();
            consumer.accept(currentNode.getData());

            if (currentNode.getRight() != null) {
                stack.push(currentNode.getRight());
            }

            if (currentNode.getLeft() != null) {
                stack.push(currentNode.getLeft());
            }
        }
    }
}