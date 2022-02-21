package ru.academits.podlatov.list;

import java.util.NoSuchElementException;
import java.util.Objects;

public class List<T> {
    private Node<T> head;
    private int size;

    public int getSize() {
        return size;
    }

    public T getFirst() {
        checkEmpty();

        return head.getData();
    }

    private Node<T> getNode(int index) {
        checkIndex(index);

        Node<T> currentNode = head;

        for (int i = 0; i < index; i++) {
            currentNode = currentNode.getNext();
        }

        return currentNode;
    }

    public T get(int index) {
        return getNode(index).getData();
    }

    public T set(int index, T data) {
        Node<T> node = getNode(index);
        T oldData = node.getData();
        node.setData(data);

        return oldData;
    }

    private T removeFirst() {
        checkEmpty();

        T removedData = head.getData();
        head = head.getNext();
        size--;
        return removedData;
    }

    public T removeByIndex(int index) {
        checkIndex(index);

        if (index == 0) {
            return removeFirst();
        }

        Node<T> previousNode = getNode(index - 1);
        Node<T> removedNode = previousNode.getNext();

        T removedData = removedNode.getData();
        previousNode.setNext(removedNode.getNext());

        size--;

        return removedData;
    }

    public boolean removeByData(T data) {
        Node<T> currentNode = head;
        Node<T> previousNode = null;

        while (currentNode != null) {
            if (Objects.equals(currentNode.getData(), data)) {
                if (previousNode != null) {
                    previousNode.setNext(currentNode.getNext());
                } else {
                    head = currentNode.getNext();
                }

                size--;
                return true;
            }

            previousNode = currentNode;
            currentNode = currentNode.getNext();
        }

        return false;
    }

    public void insertFirst(T data) {
        head = new Node<>(data, head);
        size++;
    }

    public void insertByIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index = " + index + ". valid range: [0, " + size + "]");
        }

        if (index == 0) {
            insertFirst(data);
            return;
        }

        Node<T> previousNode = getNode(index - 1);
        previousNode.setNext(new Node<>(data, previousNode.getNext()));
        size++;
    }

    public void revert() {
        if (size <= 1) {
            return;
        }

        Node<T> previousNode = null;
        Node<T> currentNode = head;
        Node<T> nextNode = head.getNext();

        while (nextNode != null) {
            currentNode.setNext(previousNode);
            previousNode = currentNode;
            currentNode = nextNode;
            nextNode = currentNode.getNext();
        }

        currentNode.setNext(previousNode);
        head = currentNode;
    }

    public List<T> copy() {
        if (head == null) {
            return new List<>();
        }

        List<T> listCopy = new List<>();
        listCopy.head = new Node<>(head.getData());
        listCopy.size = size;

        for (Node<T> node = head.getNext(), nodeCopy = listCopy.head; node != null; node = node.getNext()) {
            nodeCopy.setNext(new Node<>(node.getData()));
            nodeCopy = nodeCopy.getNext();
        }

        return listCopy;
    }

    private void checkEmpty() {
        if (head == null) {
            throw new NoSuchElementException("List is empty.");
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index = " + index + ". valid range: [0, " + (size - 1) + "]");
        }
    }

    @Override
    public String toString() {
        if (head == null) {
            return "[]";
        }

        StringBuilder stringBuilder = new StringBuilder("[");

        Node<T> node = head;

        while (node.getNext() != null) {
            stringBuilder.append(node.getData());
            stringBuilder.append(", ");
            node = node.getNext();
        }

        stringBuilder.append(node.getData());
        stringBuilder.append("]");

        return stringBuilder.toString();
    }
}