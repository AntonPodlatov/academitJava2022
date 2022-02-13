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

        return head.get();
    }

    private Node<T> getNode(int index) {
        checkIndex(index);

        Node<T> node = head;

        int i = 0;
        while (i < index) {
            node = node.getNext();
            i++;
        }

        return node;
    }

    public T get(int index) {
        return getNode(index).get();
    }

    public T set(int index, T data) {
        Node<T> node = getNode(index);
        T oldData = node.get();
        node.set(data);

        return oldData;
    }

    private T removeFirst() {
        checkEmpty();

        T removedData = head.get();
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
        T removedData = getNode(index).get();
        previous.setNext(getNode(index).getNext());

        size--;

        return removedData;
    }

    public boolean removeByData(T data) {
        Node<T> current = head;
        Node<T> previous = null;

        while (current != null) {
            if (data == current.get() || current.get().equals(data)) {
                if (previous != null) {
                    previous.setNext(current.getNext());
                } else {
                    head = current.getNext();
                }

                size--;
                return true;
            }

            previous = current;
            current = current.getNext();
        }

        return false;
    }

    public void insertFirst(T data) {
        head = new Node<>(head, data);
        size++;
    }

    public void insertByIndex(int index, T data) {
        checkIndex(index);

        if (index == 0) {
            insertFirst(data);
        }

        Node<T> previous = getNode(index - 1);
        previous.setNext(new Node<>(previous.getNext(), data));
        size++;
    }

    public void revert() {
        if (head == null) {
            return;
        } else if (size == 1) {
            return;
        }

        Node<T> prevNode = null;
        Node<T> node = head;
        Node<T> nextNode = head.getNext();

        while (nextNode != null) {
            node.setNext(prevNode);
            prevNode = node;
            node = nextNode;
            nextNode = node.getNext();
        }

        node.setNext(prevNode);
        head = node;
    }

    public List<T> copy() {
        checkEmpty();

        List<T> listCopy = new List<>();
        listCopy.head = new Node<>(head.get());
        listCopy.size = size;

        for (Node<T> node = head.getNext(), nodeCopy = listCopy.head; node != null;
             node = node.getNext()) {

            nodeCopy.setNext(new Node<>(node.get()));
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
            stringBuilder.append(node.get());
            stringBuilder.append(", ");
            node = node.getNext();
        }

        stringBuilder.append(node.get());
        stringBuilder.append("]");

        return stringBuilder.toString();
    }
}
