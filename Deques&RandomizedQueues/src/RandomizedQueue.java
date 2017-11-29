import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Sven Westerlaken on 15-10-2017.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int n = 0;

    public RandomizedQueue() {
        items = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();

        if (n == items.length) {
            resize(2 * items.length);
        }

        items[n++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();

        int random = StdRandom.uniform(n);
        Item item = items[random];
        items[random] = items[--n];
        items[n] = null;

        if (n > 0 && n == items.length / 4) {
            resize(items.length / 2);
        }

        return item;
    }

    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        return items[StdRandom.uniform(n)];
    }

    private void resize(int cap) {
        Item[] copy = (Item[]) new Object[cap];
        System.arraycopy(items, 0, copy, 0, n);
        items = copy;
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {
        private int a = 0;

        public RandomIterator() {
            StdRandom.shuffle(items, 0, n);
        }

        @Override
        public boolean hasNext() {
            return a < n;
        }

        @Override
        public Item next() {
            if (isEmpty()) throw new NoSuchElementException();
            return items[a++];
        }
    }
}
