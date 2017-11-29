import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Sven Westerlaken on 15-10-2017.
 */
public class Deque<Item> implements Iterable<Item> {

    private Node first, last;
    private int size;

    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();

        Node oldFirst = first;

        first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.previous = null;

        if (isEmpty()) {
            last = first;
        } else {
            oldFirst.previous = first;
        }

        size++;
    }

    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();

        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.previous = oldLast;

        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
        }

        size++;
    }

    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();

        Item item = first.item;
        first = first.next;
        if (isEmpty()) {
            last = null;
        }

        size--;
        return item;
    }

    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();

        Item item = last.item;
        last = last.previous;
        if (isEmpty()) {
            first = last;
        }

        size--;
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {

            private Node current = first;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Item next() {
                if (isEmpty()) throw new NoSuchElementException();

                Item item = current.item;
                current = current.next;
                return item;
            }
        };
    }
}
