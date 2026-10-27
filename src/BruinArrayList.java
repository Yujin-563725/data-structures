import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.function.UnaryOperator;

/**
 * An array-backed list that stores elements of type E.
 *
 * Students must implement the ten TODO methods.
 */
public class BruinArrayList<E> implements List<E> {

    /*
     * Java does not allow direct creation of a generic array.
     * The unchecked cast is safe here because the array is private,
     * and the class stores and returns only values of type E.
     */
    @SuppressWarnings("unchecked")
    private E[] data = (E[]) new Object[10];

    private int size;

    /**
     * Creates an empty list with a default capacity of 10.
     */
    public BruinArrayList() {
        this(10);
    }

    /**
     * Creates an empty list with the specified initial capacity.
     *
     * @param capacity initial array capacity
     * @throws IllegalArgumentException if capacity is less than or equal to zero
     */
    @SuppressWarnings("unchecked")
    public BruinArrayList(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero");
        }

        /*
         * Java does not allow direct creation of a generic array.
         * This unchecked cast is safe because the array is private and
         * is used only to store elements accepted as type E.
         */
        data = (E[]) new Object[capacity];
        size = 0;
    }

    /**
     * Appends an element to the end of the list.
     *
     * @param e element to append
     * @return true after the element is added
     */
    @Override
    public boolean add(E e) {
        if (data.length == size) {
            E[] temp = (E[]) new Object[size+10];
            for (int i = 0; i < size; i++) {
                temp[i] = data[i];
            }
            data = temp;
        }
        data[size] = e;
        size++;
        return true;
    }

    /**
     * Inserts an element at the specified index.
     *
     * Valid indices range from 0 through size(), inclusive.
     *
     * @param index insertion index
     * @param element element to insert
     * @throws IndexOutOfBoundsException if index is invalid
     */
    @Override
    public void add(int index, E element) {
        if (index < 0 || index > size) {throw new IndexOutOfBoundsException();}
        if (data.length == size) {
            E[] temp = (E[]) new Object[size+10];
            for (int i = 0; i < size; i++) {
                temp[i] = data[i];
            }
            data = temp;
        }
        E[] temp = (E[]) new Object[data.length];
        for (int i = 0; i < index; i++) {
            temp[i] = data[i];
        }
        temp[index] = element;
        for (int i = index; i < size; i++) {
            temp[i+1] = data[i];
        }
        size++;
        data = temp;
    }

    /**
     * Removes all elements from the list.
     */
    @Override
    public void clear() {
        data = (E[]) new Object[data.length];
        size = 0;
    }

    /**
     * Returns the element at the specified index.
     *
     * @param index element index
     * @return element at index
     * @throws IndexOutOfBoundsException if index is invalid
     */
    @Override
    public E get(int index) {
        if (index < 0 || index >= size) {throw new IndexOutOfBoundsException();}
        return data[index];
    }

    /**
     * Returns the index of the first occurrence of an object.
     *
     * The parameter remains Object because this method follows the
     * java.util.List<E> interface contract.
     *
     * @param o object to find
     * @return first matching index, or -1 if not found
     */
    @Override
    public int indexOf(Object o) {
        E e2 = (E) o;
        for (int i = 0; i < size; i++) {
            E e = data[i];
            if (e.equals(e2)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns whether the list contains no elements.
     *
     * @return true when the list is empty
     */
    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Removes and returns the element at the specified index.
     *
     * @param index element index
     * @return removed element
     * @throws IndexOutOfBoundsException if index is invalid
     */
    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) {throw new IndexOutOfBoundsException();}
        E[] temp = (E[]) new Object[data.length];
        E e = data[index];
        int i = 0;
        while (i < index) {
            temp[i] = data[i];
            i++;
        }
        i++;
        while (i < size) {
            temp[i-1] = data[i];
            i++;
        }
        data = temp;
        size--;
        return e;
    }

    /**
     * Removes the first occurrence of an object.
     *
     * The parameter remains Object because this method follows the
     * java.util.List<E> interface contract.
     *
     * @param o object to remove
     * @return true if an object was removed
     */
    @Override
    public boolean remove(Object o) {
        E e = (E) o;
        E[] temp = (E[]) new Object[data.length];
        int i = 0;
        boolean found = false;
        while (i < size) {
            if (data[i].equals(e)) {
                found = true;
                break;
            } else {
                temp[i] = data[i];
            }
            i++;
        }
        if (!found) {return false;}
        i++;
        while (i < size) {
            temp[i-1] = data[i];
            i++;
        }
        data = temp;
        size--;
        return true;
    }

    /**
     * Replaces the element at the specified index.
     *
     * @param index element index
     * @param element replacement element
     * @return the element previously stored at index
     * @throws IndexOutOfBoundsException if index is invalid
     */
    @Override
    public E set(int index, E element) {
        if (index < 0 || index >= size) {throw new IndexOutOfBoundsException();}
        E prev = data[index];
        data[index] = element;
        return prev;
    }

    /**
     * Returns the number of elements in the list.
     *
     * @return logical list size
     */
    @Override
    public int size() {
        return size;
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    /**
     * Returns whether an object is present in the list.
     *
     * @param o object to find
     * @return true when the object is present
     */
    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    /**
     * Returns the index of the last occurrence of an object.
     *
     * @param o object to find
     * @return last matching index, or -1 if not found
     */
    @Override
    public int lastIndexOf(Object o) {
        for (int i = size() - 1; i >= 0; i--) {
            if (Objects.equals(get(i), o)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Appends every element from the specified collection.
     *
     * @param c collection whose elements will be appended
     * @return true if the list changed
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        Objects.requireNonNull(c);

        boolean changed = false;
        for (E element : c) {
            add(element);
            changed = true;
        }
        return changed;
    }

    /**
     * Inserts every element from the specified collection.
     *
     * @param index insertion index
     * @param c collection whose elements will be inserted
     * @return true if the list changed
     * @throws IndexOutOfBoundsException if index is invalid
     */
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        Objects.requireNonNull(c);

        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }

        int originalSize = size();
        int offset = 0;

        for (E element : c) {
            add(index + offset, element);
            offset++;
        }

        return size() != originalSize;
    }

    /**
     * Returns whether this list contains every element in the collection.
     *
     * @param c collection to check
     * @return true when all elements are present
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        Objects.requireNonNull(c);

        for (Object element : c) {
            if (!contains(element)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Retains only elements that occur in the specified collection.
     *
     * @param c collection of elements to retain
     * @return true if the list changed
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        Objects.requireNonNull(c);

        boolean changed = false;

        for (int i = size() - 1; i >= 0; i--) {
            if (!c.contains(get(i))) {
                remove(i);
                changed = true;
            }
        }

        return changed;
    }

    /**
     * Removes every element that occurs in the specified collection.
     *
     * @param c collection of elements to remove
     * @return true if the list changed
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c);

        boolean changed = false;

        for (int i = size() - 1; i >= 0; i--) {
            if (c.contains(get(i))) {
                remove(i);
                changed = true;
            }
        }

        return changed;
    }

    /**
     * Returns a new iterator beginning at index zero.
     *
     * @return iterator over elements of type E
     */
    @Override
    public Iterator<E> iterator() {
        return new BruinArrayListIterator<E>(this);
    }

    /**
     * List iterators are not required for this assignment.
     *
     * @return no list iterator
     * @throws UnsupportedOperationException always
     */
    @Override
    public ListIterator<E> listIterator() {return new BruinArrayListIterator<>(this);

    }

    /**
     * List iterators are not required for this assignment.
     *
     * @param index starting index
     * @return no list iterator
     * @throws UnsupportedOperationException always
     */
    @Override
    public ListIterator<E> listIterator(int index) {
        checkPositionIndex(index);
        return new BruinArrayListIterator<>(this, index);
    }

    // ????
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return List.of();
    }

    /**
     * Returns the list contents as a new array.
     *
     * @return array containing all logical elements
     */
    @Override
    public Object[] toArray() {
        Object[] result = new Object[size()];

        for (int i = 0; i < size(); i++) {
            result[i] = get(i);
        }

        return result;
    }

    /**
     * Returns the list contents in the specified array when possible.
     *
     * @param a destination array
     * @param <T> array element type
     * @return array containing the list contents
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        Objects.requireNonNull(a);

        if (a.length < size()) {
            a = (T[]) java.lang.reflect.Array.newInstance(
                    a.getClass().getComponentType(),
                    size());
        }

        for (int i = 0; i < size(); i++) {
            a[i] = (T) get(i);
        }

        if (a.length > size()) {
            a[size()] = null;
        }

        return a;
    }

    /**
     * Compares this list with another object element by element.
     *
     * @param other object to compare
     * @return true when the objects represent equal lists
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof List<?>)) {
            return false;
        }

        List<?> otherList = (List<?>) other;

        if (otherList.size() != size()) {
            return false;
        }

        Iterator<?> thisIterator = iterator();
        Iterator<?> otherIterator = otherList.iterator();

        while (thisIterator.hasNext() && otherIterator.hasNext()) {
            if (!Objects.equals(thisIterator.next(), otherIterator.next())) {
                return false;
            }
        }

        return !thisIterator.hasNext() && !otherIterator.hasNext();
    }

    /**
     * Computes a list hash code using the standard List contract.
     *
     * @return list hash code
     */
    @Override
    public int hashCode() {
        int result = 1;

        for (E element : this) {
            result = 31 * result + Objects.hashCode(element);
        }

        return result;
    }

    /**
     * Returns the elements in list order.
     *
     * @return string representation
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("[");

        Iterator<E> iterator = iterator();

        while (iterator.hasNext()) {
            result.append(String.valueOf(iterator.next()));

            if (iterator.hasNext()) {
                result.append(", ");
            }
        }

        result.append("]");
        return result.toString();
    }

//    /**
//     * Returns the default spliterator.
//     *
//     * @return spliterator over this list
//     */
//    @Override
//    public Spliterator<E> spliterator() {
//        return List.super.spliterator();
//    }

    public static void main(String[] args) {
        BruinArrayList<String> list = new BruinArrayList<String>();
        list.get(0);
        // System.out.println(list);
    }
}