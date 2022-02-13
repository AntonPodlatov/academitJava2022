package ru.academits.podlatov.list;

class Node<T> {
    private Node<T> next;
    private T data;

    protected Node(Node<T> next, T data) {
        this.next = next;
        this.data = data;
    }

    protected Node(T data) {
        this.data = data;
    }

    protected Node<T> getNext() {
        return next;
    }

    protected void setNext(Node<T> next) {
        this.next = next;
    }

    protected T get() {
        return data;
    }

    protected void set(T data) {
        this.data = data;
    }
}
