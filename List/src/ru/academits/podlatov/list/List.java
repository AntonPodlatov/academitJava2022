package ru.academits.podlatov.list;

import java.util.NoSuchElementException;

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

        Node<T> node = head;

        for (int i = 0; i < index; i++) {
            node = node.getNext();
        }

        return node;
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

        Node<T> previous = getNode(index - 1);
        Node<T> removedNode = previous.getNext();

        T removedData = removedNode.getData();
        previous.setNext(removedNode.getNext());

        size--;

        return removedData;
    }

    public boolean removeByData(T data) {
        Node<T> current = head;
        Node<T> previous = null;

        while (current != null) {
            if (data == null) {
                if (current.getData() == null) {
                    if (previous != null) {
                        previous.setNext(current.getNext());
                    } else {
                        head = current.getNext();
                    }

                    size--;
                    return true;
                }
            } else {
                if (data.equals(current.getData())) {
                    if (previous != null) {
                        previous.setNext(current.getNext());
                    } else {
                        head = current.getNext();
                    }

                    size--;
                    return true;
                }
            }

            previous = current;
            current = current.getNext();
        }

        return false;
    }

    public void insertFirst(T data) {
        head = new Node<>(data, head);
        size++;
    }

    public void insertByIndex(int index, T data) {
        checkIndex(index);

        if (index == 0) {
            insertFirst(data);
        }

        Node<T> previous = getNode(index - 1);
        previous.setNext(new Node<>(data, previous.getNext()));
        size++;
    }

    public void revert() {
        if (head == null || size == 1) {
            return;
        }

        Node<T> previous = null;
        Node<T> current = head;
        Node<T> next = head.getNext();

        while (next != null) {
            current.setNext(previous);
            previous = current;
            current = next;
            next = current.getNext();
        }

        current.setNext(previous);
        head = current;
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
