package ru.academits.podlatov.array_list;

import java.util.*;
import java.util.function.UnaryOperator;

public class ArrayList<T> implements List<T> {
    private T[] elements;
    private int size;
    private int modCount;

    public ArrayList() {
        final int defaultCapacity = 10;
        //noinspection unchecked
        elements = (T[]) new Object[defaultCapacity];
    }

    public ArrayList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("capacity = " + capacity + ". Capacity cant be < 0.");
        }
        //noinspection unchecked
        elements = (T[]) new Object[capacity];
    }

    public void increaseCapacity() {
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
            if (elements[i].equals(o)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int lastIndex = -1;

        for (int i = 0; i < size; i++) {
            if (elements[i].equals(o)) {
                lastIndex = i;
            }
        }

        return lastIndex;
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
        checkIndex(index);

        if (size - index >= elements.length) {
            increaseCapacity();
        }

        System.arraycopy(elements, index, elements, index + 1, size - index);

        elements[index] = element;
        size++;
        modCount++;
    }

    @Override
    public boolean add(T t) {
        if (size >= elements.length) {
            increaseCapacity();
        }

        elements[size] = t;
        size++;
        modCount++;

        return true;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);

        T oldElement = elements[index];

        if (index < size - 1) {
            System.arraycopy(elements, index + 1, elements, index, size - index - 1);
        }

        size--;
        modCount++;

        return oldElement;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);

        if (index != 0) {
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
        for (int i = 0; i < size; i++) {
            if (!c.contains(elements[i])) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (T t : c) {
            add(t);
        }

        return c.size() != 0;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        checkIndex(index);

        //noinspection unchecked
        T[] array = (T[]) c.toArray();

        if (size - index + c.size() >= elements.length) {
            elements = Arrays.copyOf(elements, elements.length + array.length);
        }
        if (index < size - 1) {
            System.arraycopy(elements, index, elements, index + c.size(), size - index);
        }

        System.arraycopy(array, 0, elements, index, array.length);
        size += array.length;
        modCount += elements.length - index;

        return array.length != 0;
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
    public void replaceAll(UnaryOperator<T> operator) {
        List.super.replaceAll(operator);
    }

    @Override
    public void sort(Comparator<? super T> c) {
        //noinspection unchecked
        T[] elementsCopy = (T[]) this.toArray();
        Arrays.sort(elementsCopy, c);

        Iterator<T> iterator = new ArrayListIterator();

        int i = 0;
        for (T t : elementsCopy) {
            iterator.next();
            this.set(i, t);
            i++;
        }
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }

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

    @Override
    public Spliterator<T> spliterator() {
        return List.super.spliterator();
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
        if (index >= size || index < 0) {
            throw new IllegalArgumentException("Index = " + index + ". Valid range: {0, " + size + "}.");
        }
    }

    private class ArrayListIterator implements Iterator<T> {
        private int currentIndex = -1;
        private int modCountAtTimeOfCreation;

        public Iterator<T> iterator() {
            modCountAtTimeOfCreation = ArrayList.this.modCount;
            return new ArrayListIterator();
        }

        @Override
        public boolean hasNext() {
            return currentIndex + 1 < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("There is no next element. The list is over.");
            } else if (modCountAtTimeOfCreation != modCount) {
                throw new ConcurrentModificationException("The size of the list has changed during the crawl.");
            }

            currentIndex++;
            return elements[currentIndex];
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOf(elements, size));
    }
}
