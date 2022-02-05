package ru.academits.podlatov.list;

import ru.academits.podlatov.list.node.Node;

import java.util.NoSuchElementException;

public class List<T> {
    private Node<T> head;
    private int size;

    public int getSize() {
        return size;
    }

    public T getHeadsData() {
        checkIfEmpty();

        return head.getData();
    }

    private void checkIfEmpty() {
        if (head == null) {
            throw new NoSuchElementException("List is empty.");
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index = " + index + ". valid range: [0, " + (size - 1) + "]");
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        checkIfEmpty();

        Node<T> node = head;

        int i = 0;
        while (i < index) {
            node = node.getNext();
            i++;
        }

        return node;
    }

    public T getData(int index) {
        return getNode(index).getData();
    }

    public T setData(int index, T data) {
        Node<T> node = getNode(index);
        T oldData = node.getData();
        node.setData(data);

        return oldData;
    }

    public T removeNode(int index) {
        checkIfEmpty();
        checkIndex(index);

        if (index == 0) {
            return removeHead();
        }

        Node<T> previous = getNode(index - 1);
        T oldData = getNode(index).getData();
        previous.setNext(getNode(index).getNext());

        size--;

        return oldData;
    }

    private T removeHead() {
        checkIfEmpty();

        T oldData = head.getData();
        head = head.getNext();
        size--;
        return oldData;
    }

    public void insertAtBeginning(T data) {
        head = new Node<>(head, data);
        size++;
    }

    public void insertByIndex(int index, T data) {
        checkIndex(index);

        if (index == 0) {
            insertAtBeginning(data);
        }

        Node<T> prev = getNode(index - 1);
        prev.setNext(new Node<>(prev.getNext(), data));
        size++;
    }

    public boolean removeNodeByData(T data) {
        Node<T> node = head;
        Node<T> prevNode = null;

        while (node != null) {
            if (data.equals(node.getData())) {
                if (prevNode != null) {
                    prevNode.setNext(node.getNext());
                } else {
                    head = node.getNext();
                }
                size--;

                return true;
            }
            prevNode = node;
            node = node.getNext();
        }

        return false;
    }

    public void revert() {
        checkIfEmpty();

        if (size == 1) {
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
        checkIfEmpty();

        List<T> listCopy = new List<>();
        listCopy.head = new Node<>(head.getData());
        listCopy.size = size;

        for (Node<T> node = head.getNext(), nodeCopy = listCopy.head; node != null;
             node = node.getNext()) {

            nodeCopy.setNext(new Node<>(node.getData()));
            nodeCopy = nodeCopy.getNext();
        }
        return listCopy;
    }

    @Override
    public String toString() {
        checkIfEmpty();

        StringBuilder stringBuilder = new StringBuilder("{");

        Node<T> node = head;

        while (node.getNext() != null) {
            stringBuilder.append(node.getData());
            stringBuilder.append(", ");
            node = node.getNext();
        }

        stringBuilder.append(node.getData());
        stringBuilder.append("}");

        return stringBuilder.toString();
    }
}
