import java.util.ListIterator;
import java.util.NoSuchElementException;

public class BruinArrayListIterator<E> implements ListIterator<E> {

    private final BruinArrayList<E> list;
    private int cursor;            // index of next element to return
    private int lastReturned = -1; // index of last element returned

    BruinArrayListIterator(BruinArrayList<E> list) {
        this(list, 0);
    }

    BruinArrayListIterator(BruinArrayList<E> list, int index) {
        this.list = list;
        this.cursor = index;
    }

    @Override
    public boolean hasNext() {
        return cursor < list.size(); // logical size, not capacity
    }

    @Override
    public E next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return list.get(lastReturned = cursor++);
    }

    @Override
    public boolean hasPrevious() {
        return cursor > 0;
    }

    @Override
    public E previous() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        return list.get(lastReturned = --cursor);
    }

    @Override
    public int nextIndex() {
        return cursor;
    }

    @Override
    public int previousIndex() {
        return cursor - 1;
    }

    @Override
    public void remove() {
        if (lastReturned < 0) {
            throw new IllegalStateException("next() or previous() not called");
        }
        list.remove(lastReturned);
        cursor = lastReturned;   // element shifted left under us
        lastReturned = -1;
    }

    @Override
    public void set(E e) {
        if (lastReturned < 0) {
            throw new IllegalStateException("next() or previous() not called");
        }
        list.set(lastReturned, e);
    }

    @Override
    public void add(E e) {
        list.add(cursor, e);
        cursor++;
        lastReturned = -1;
    }
}