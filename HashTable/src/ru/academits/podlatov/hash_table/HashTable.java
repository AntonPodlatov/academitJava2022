package ru.academits.podlatov.hash_table;

import java.util.*;
import java.util.function.Predicate;

public class HashTable<T> implements Collection<T> {
    private ArrayList<T>[] listsArray;
    int elementsCount;
    private int modCount;

    public HashTable(int capacity) {
        if (capacity < 0) {
            throw new IndexOutOfBoundsException("Capacity = " + capacity + ". Capacity cant be < 0.");
        }
        //noinspection unchecked
        listsArray = (ArrayList<T>[]) new ArrayList[capacity];
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
        int passedTotalCount = 0;
        int passedListElementsCount = 0;
        int modificationsCount = modCount;
        int arrayIndex = 0;
        int listIndex = -1;

        @Override
        public boolean hasNext() {
            return passedTotalCount < elementsCount;
        }

        @Override
        public T next() {
            if (modificationsCount != modCount) {
                throw new ConcurrentModificationException("Elements count in the table has changed during the crawl.");
            } else if (!hasNext()) {
                throw new NoSuchElementException("Еnd of table.");
            }

            while (listsArray[arrayIndex] == null) {
                arrayIndex++;
            }

            if (listIndex + 1 < listsArray[arrayIndex].size()) {
                listIndex++;
            } else {
                listIndex = 0;
                passedListElementsCount = 0;
                arrayIndex++;

                while (listsArray[arrayIndex] == null) {
                    arrayIndex++;
                }
            }
            passedListElementsCount++;
            passedTotalCount++;

            return listsArray[arrayIndex].get(listIndex);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new HashTableIterator();
    }

    @Override
    public boolean add(T t) {
        if (t == null) {
            throw new IllegalArgumentException("Element to be added is null.");
        }

        int listsArrayIndex = Math.abs(t.hashCode() % listsArray.length);

        if (listsArray[listsArrayIndex] == null) {
            ArrayList<T> newList = new ArrayList<>(1);
            newList.add(t);
            listsArray[listsArrayIndex] = newList;
            elementsCount++;
            modCount++;

            return true;
        }
        listsArray[listsArrayIndex].add(t);
        elementsCount++;
        modCount++;

        return false;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            throw new IllegalArgumentException("Item to be removed is null.");
        }

        int listsArrayIndex = Math.abs(o.hashCode() % listsArray.length);

        if (listsArray[listsArrayIndex] == null) {
            return false;
        }

        if (listsArray[listsArrayIndex].remove(o)) {
            modCount++;
            elementsCount--;
            return true;
        }

        return false;
    }

    @Override
    public boolean contains(Object o) {
        int arrayListIndex = Math.abs(o.hashCode() % listsArray.length);

        return listsArray[arrayListIndex] != null && (listsArray[arrayListIndex].contains(o));
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c == null) {
            throw new IllegalArgumentException("Collection is null.");
        }

        if (c.size() == 0) {
            return false;
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

        for (T t : c) {
            add(t);
        }

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException("Collection is null.");
        } else if (c.size() == 0) {
            return false;
        }

        int modCountAfterRemoveAll = modCount;

        for (Object o : c) {
            remove(o);
        }

        return !(modCountAfterRemoveAll == modCount);
    }

    @Override
    public boolean removeIf(Predicate<? super T> filter) {
        return Collection.super.removeIf(filter);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException("Collection is null.");
        } else if (c.size() == 0) {
            return false;
        }

        int modCountAfterRetainAll = modCount;
        ArrayList<T> elementsListToRemove = new ArrayList<>();

        for (T t : this) {
            if (!c.contains(t)) {
                elementsListToRemove.add(t);
            }
        }

        for (T t : elementsListToRemove) {
            remove(t);
        }

        return !(modCountAfterRetainAll == modCount);
    }

    @Override
    public void clear() {
        for (ArrayList<T> list : listsArray) {
            list.clear();
        }
        elementsCount = 0;
        modCount = 0;
    }

    @Override
    public Object[] toArray() {
        Object[] objects = new Object[elementsCount];
        Iterator<T> iterator = iterator();

        int i = 0;
        while (iterator.hasNext()) {
            objects[i] = iterator.next();
            i++;
        }
        return objects;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < elementsCount) {
            //noinspection unchecked
            return (T1[]) Arrays.copyOf(listsArray, elementsCount, a.getClass());
        }

        //noinspection SuspiciousSystemArraycopy
        System.arraycopy(listsArray, 0, a, 0, elementsCount);

        if (a.length > elementsCount) {
            a[elementsCount] = null;
        }

        return a;
    }

    @Override
    public String toString() {
        return  Arrays.toString(listsArray);
    }
}
