package ru.academits.podlatov.hash_table;

import java.util.*;

public class HashTable<T> implements Collection<T> {
    private final ArrayList<T>[] lists;
    private int elementsCount;
    private int modCount;

    public HashTable(int arrayLength) {
        if (arrayLength <= 0) {
            throw new IndexOutOfBoundsException("Array length = " + arrayLength + ". Capacity cant be <= 0.");
        }

        //noinspection unchecked
        lists = (ArrayList<T>[]) new ArrayList[arrayLength];
    }

    @Override
    public int size() {
        return elementsCount;
    }

    @Override
    public boolean isEmpty() {
        return elementsCount == 0;
    }

    private class HashTableIterator implements Iterator<T> {
        private int arrayIndex = 0;
        private int listIndex = -1;
        private int passedTotalCount = 0;
        private final int modCountAtCreationTime = modCount;


        @Override
        public boolean hasNext() {
            return passedTotalCount < elementsCount;
        }

        @Override
        public T next() {
            if (modCountAtCreationTime != modCount) {
                throw new ConcurrentModificationException("Elements count in the table has changed during the crawl.");
            }

            if (!hasNext()) {
                throw new NoSuchElementException("Еnd of table.");
            }

            while (lists[arrayIndex] == null) {
                arrayIndex++;
            }

            if (listIndex + 1 < lists[arrayIndex].size()) {
                listIndex++;
            } else {
                listIndex = 0;
                arrayIndex++;

                while (lists[arrayIndex] == null) {
                    arrayIndex++;
                }
            }

            passedTotalCount++;

            return lists[arrayIndex].get(listIndex);
        }

        @Override
        public void remove() {
            Iterator.super.remove();
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new HashTableIterator();
    }

    @Override
    public boolean add(T element) {
        int listsIndex = getIndex(element);

        if (lists[listsIndex] == null) {
            ArrayList<T> newList = new ArrayList<>(1);
            lists[listsIndex] = newList;
        }

        lists[listsIndex].add(element);
        elementsCount++;
        modCount++;

        return true;
    }

    private int getIndex(Object o) {
        return Math.abs(Objects.hashCode(o) % lists.length);
    }

    @Override
    public boolean contains(Object o) {
        int arrayListIndex = getIndex(o);

        return lists[arrayListIndex] != null && lists[arrayListIndex].contains(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c == null) {
            throw new IllegalArgumentException("Collection is null.");
        }

        if (c.size() == 0) {
            return true;
        }

        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (c == null) {
            throw new IllegalArgumentException("Collection is null.");
        }

        if (c.size() == 0) {
            return false;
        }

        for (T element : c) {
            add(element);
        }

        return true;
    }

    @Override
    public boolean remove(Object o) {
        int listsArrayIndex = getIndex(o);

        if (lists[listsArrayIndex] == null) {
            return false;
        }

        if (lists[listsArrayIndex].remove(o)) {
            modCount++;
            elementsCount--;
            return true;
        }

        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException("Collection is null.");
        }

        if (c.size() == 0) {
            return false;
        }

        int modCountBeforeRemoveAll = modCount;

        for (ArrayList<T> list : lists) {
            if (list != null) {
                if (list.removeAll(c)) {
                    modCount++;
                }
            }
        }

        return modCountBeforeRemoveAll != modCount;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException("Collection is null.");
        }

        if (c.size() == 0) {
            clear();
        }

        int modCountBeforeRetainAll = modCount;

        for (ArrayList<T> list : lists) {
            if (list != null) {
                if (list.retainAll(c)) {
                    modCount++;
                }
            }
        }

        return modCountBeforeRetainAll != modCount;
    }

    @Override
    public void clear() {
        if (isEmpty()) {
            return;
        }

        for (ArrayList<T> list : lists) {
            if (list == null) {
                continue;
            }

            list.clear();
        }

        elementsCount = 0;
        modCount++;
    }

    @Override
    public Object[] toArray() {
        Object[] objects = new Object[elementsCount];

        int i = 0;
        for (Object o : this) {
            objects[i] = o;
            i++;
        }

        return objects;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < elementsCount) {
            //noinspection unchecked
            return (T1[]) Arrays.copyOf(toArray(), elementsCount, a.getClass());
        }

        int i = 0;
        for (T element : this) {
            //noinspection unchecked
            a[i] = (T1) element;
            i++;
        }

        if (a.length > elementsCount) {
            a[elementsCount] = null;
        }

        return a;
    }

    @Override
    public String toString() {
        return Arrays.toString(lists);
    }
}