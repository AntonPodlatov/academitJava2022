package ru.academits.podlatov.array_list;

import java.util.*;

public class ArrayList<T> implements List<T> {
    private T[] elements;
    private int size;
    private int modCount;
    final private static int initialCapacity = 10;

    public ArrayList(int capacity) {
        if (capacity < 0) {
            throw new IndexOutOfBoundsException("capacity = " + capacity + ". Capacity can't be < 0.");
        }

        //noinspection unchecked
        elements = (T[]) new Object[capacity];
    }

    public ArrayList() {
        this(initialCapacity);
    }

    private void increaseCapacity() {
        if (size == 0) {
            //noinspection unchecked
            elements = (T[]) new Object[initialCapacity];
        }

        elements = Arrays.copyOf(elements, elements.length * 2);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(elements[i], o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            if (Objects.equals(elements[i], o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    @Override
    public T get(int index) {
        checkIndex(index);

        return elements[index];
    }

    @Override
    public T set(int index, T element) {
        checkIndex(index);

        T oldElement = elements[index];
        elements[index] = element;

        return oldElement;
    }

    @Override
    public void add(int index, T element) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index = " + index + ". Valid range: {0, " + size + "}.");
        }

        if (size == elements.length) {
            increaseCapacity();
        }

        System.arraycopy(elements, index, elements, index + 1, size - index);

        elements[index] = element;
        size++;
        modCount++;
    }

    @Override
    public boolean add(T element) {
        add(size, element);
        return true;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T removedElement = elements[index];

        if (index < size - 1) {
            System.arraycopy(elements, index + 1, elements, index, size - index - 1);
        }

        elements[size - 1] = null;
        size--;
        modCount++;

        return removedElement;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);

        if (index != -1) {
            remove(index);
            return true;
        }

        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayListIterator();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elements, size);
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < size) {
            //noinspection unchecked
            return (T1[]) Arrays.copyOf(elements, size, a.getClass());
        }

        //noinspection SuspiciousSystemArraycopy
        System.arraycopy(elements, 0, a, 0, size);

        if (a.length > size) {
            a[size] = null;
        }

        return a;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return addAll(size, c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index = " + index + ". Valid range: {0, " + size + "}.");
        }

        if (c.size() == 0) {
            return false;
        }

        ensureCapacity(size + c.size());

        System.arraycopy(elements, index, elements, index + c.size(), size - index);

        int i = index;

        for (T element : c) {
            elements[i] = element;
            i++;
        }

        size += c.size();
        modCount++;

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean isRemoved = false;

        for (int i = 0; i < size; i++) {
            if (c.contains(elements[i])) {
                remove(i);
                i--;
                isRemoved = true;
            }
        }

        return isRemoved;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean isRemoved = false;

        for (int i = 0; i < size; i++) {
            if (!c.contains(elements[i])) {
                remove(i);
                i--;
                isRemoved = true;
            }
        }

        return isRemoved;
    }

    @Override
    public void clear() {
        if (isEmpty()) {
            return;
        }

        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }

        modCount++;
        size = 0;
    }

    @Override
    public ListIterator<T> listIterator() {
        return null;
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return null;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return null;
    }

    public void trimToSize() {
        if (size < elements.length) {
            elements = Arrays.copyOf(elements, size);
        }
    }

    public void ensureCapacity(int capacity) {
        if (elements.length < capacity) {
            elements = Arrays.copyOf(elements, capacity);
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index = " + index + ". Valid range: {0, " + (size - 1) + "}.");
        }
    }

    private class ArrayListIterator implements Iterator<T> {
        private int currentIndex = -1;
        private final int initialModCount = modCount;

        @Override
        public boolean hasNext() {
            return currentIndex + 1 < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("There is no next element. The list is over.");
            }

            if (initialModCount != modCount) {
                throw new ConcurrentModificationException("The size of the list has changed during the crawl.");
            }

            currentIndex++;
            return elements[currentIndex];
        }
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }

        StringBuilder stringBuilder = new StringBuilder("[");

        for (T t : this) {
            stringBuilder.append(t);
            stringBuilder.append(", ");
        }

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append("]");

        return stringBuilder.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 13;
        int hash = 1;

        for (T element : this) {
            int elementHashCode = (element == null) ? 0 : element.hashCode();

            hash = prime * hash + elementHashCode;
        }

        return Arrays.hashCode(elements);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        //noinspection unchecked
        ArrayList<T> arrayList = (ArrayList<T>) o;

        if (size != arrayList.size) {
            return false;
        }

        for (int i = 0; i < size; i++) {
            if (Objects.equals(elements[i], arrayList.elements[i])) {
                return false;
            }
        }

        return true;
    }
}