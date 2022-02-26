package ru.academits.podlatov.tree;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Tree<T> {
    private Node<T> root;
    private int size;
    private Comparator<? super T> comparator;

    public Tree() {
    }

    public Tree(Comparator<? super T> comparator) {
        this.comparator = comparator;
    }

    private int compare(T data1, T data2) {
        if (data1 == null) {
            if (data2 == null) {
                return 0;
            } else {
                return -1;
            }
        }
        if (data2 == null) {
            return 1;
        }

        //noinspection unchecked
        return (comparator == null) ?
                ((Comparable<? super T>) data1).compareTo(data2) : comparator.compare(data1, data2);
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
        insert(data, currentNode);
    }

    private void insert(T data, Node<T> currentNode) {
        if (compare(data, currentNode.getData()) < 0) {
            if (currentNode.getLeft() != null) {
                currentNode = currentNode.getLeft();
                insert(data, currentNode);
            } else {
                currentNode.setLeft(new Node<>(data));
                size++;
            }
        } else {
            if (currentNode.getRight() != null) {
                currentNode = currentNode.getRight();
                insert(data, currentNode);
            } else {
                currentNode.setRight(new Node<>(data));
                size++;
            }
        }
    }

    public Node<T> find(T data) {
        Node<T> parentNode = findParentOf(data);
        boolean isRoot = compare(data, root.getData()) == 0;

        if (parentNode == null) {
            if (isRoot) {
                return root;
            }
            return null;
        }

        Node<T> node = null;

        if (parentNode.getLeft() != null) {
            if (compare(data, parentNode.getLeft().getData()) == 0) {
                node = parentNode.getLeft();
            }
        }
        if (parentNode.getRight() != null) {
            if (compare(data, parentNode.getRight().getData()) == 0) {
                node = parentNode.getRight();
            }
        }

        return node;
    }

    public Node<T> findParentOf(T data) {
        Node<T> currentNode = root;
        Node<T> parentNode = null;

        while (true) {
            int comparisonResult = compare(data, currentNode.getData());

            if (comparisonResult == 0) {
                return parentNode;
            } else if (comparisonResult < 0) {
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

    public boolean remove(T removableData) {
        Node<T> parentNode = findParentOf(removableData);
        Node<T> removableNode = find(removableData);

        if (removableNode == null) {
            return false;
        }

        boolean removableIsLeft = compare(removableData, parentNode.getData()) < 0;

        if (removableNode.hasNoChildren()) {
            if (removableIsLeft) {
                parentNode.setLeft(null);
            } else {
                parentNode.setRight(null);
            }

            return true;
        }

        if (removableNode.hasOnlyRight()) {
            if (removableIsLeft) {
                parentNode.setLeft(removableNode.getRight());
            } else {
                parentNode.setRight(removableNode.getRight());
            }

            return true;
        }

        if (removableNode.hasOnlyLeft()) {
            if (removableIsLeft) {
                parentNode.setLeft(removableNode.getLeft());
            } else {
                parentNode.setRight(removableNode.getLeft());
            }

            return true;
        }

        if (removableNode.has2Children()) {
            removeNodeWith2Children(removableNode);

            return true;
        }

        return false;
    }

    private void removeNodeWith2Children(Node<T> removableNode) {
        Node<T> mostLeftParent = removableNode;
        Node<T> mostLeft = removableNode.getRight();

        while (mostLeft.getLeft() != null) {
            mostLeftParent = mostLeft;
            mostLeft = mostLeft.getLeft();
        }

        removableNode.setData(mostLeft.getData());

        if (mostLeft.hasOnlyRight()) {
            mostLeftParent.setLeft(mostLeft.getRight());
        } else {
            mostLeftParent.setLeft(null);
        }
    }

    public void breadthTraversal() {
        Queue<Node<T>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node<T> currentNode = queue.remove();
            System.out.print("currentNodeData: " + currentNode.getData());

            if (currentNode.getLeft() != null) {
                System.out.print(", leftNodeData: " + currentNode.getLeft().getData());
                queue.add(currentNode.getLeft());

            }

            if (currentNode.getRight() != null) {
                System.out.println(", rightNodeData: " + currentNode.getRight().getData());
                queue.add(currentNode.getRight());
            }

            System.out.println();
        }
    }

    private void visit(Node<T> node) {
        System.out.println(node);

        if (node.getLeft() != null) {
            visit(node.getLeft());
        }

        if (node.getRight() != null) {
            visit(node.getRight());
        }
    }

    public void depthTraversalRecursive() {
        visit(root);
    }

    public void depthTraversal() {
        Stack<Node<T>> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            Node<T> currentNode = stack.pop();
            System.out.println(currentNode);

            if(currentNode.getRight()!=null){
                stack.push(currentNode.getRight());
            }

            if(currentNode.getLeft()!=null){
                stack.push(currentNode.getLeft());
            }
        }
    }
}