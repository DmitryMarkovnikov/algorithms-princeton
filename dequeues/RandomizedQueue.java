import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;         // array of items
    private int size;         // number of elements on queue

    public RandomizedQueue() {
        this.items = (Item[]) new Object[2];
        this.size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new NoSuchElementException("Can't enqueue null item");
        }
        this.items[size++] = item;
        if (size == this.items.length) {
            resize(2 * this.items.length);

        }
        swapItem();
    }

    public Item dequeue() {
        if (size == 0) {
            throw new NoSuchElementException("Can't dequeue, queue is empty");
        }
        Item item = this.items[--size];
        if (size > 0 && size == this.items.length / 4) {
            resize(this.items.length / 2);
        }
        this.items[size] = null;
        return item;

    }

    public Item sample() {
        if (size == 0) {
            throw new NoSuchElementException("Can't dequeue, queue is empty");
        }
        int i = StdRandom.uniform(size);
        return this.items[i];
    }

    private void resize(int capacity) {
        assert capacity >= this.size;

        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++)
            copy[i] = items[i];
        items = copy;
    }


    private void swapItem() {
        int i = StdRandom.uniform(size);
        Item temp = items[i];
        items[i] = items[size - 1];
        items[size - 1] = temp;
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int i;

        public boolean hasNext() {
            return items[i] != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            } else {
                Item item = items[i++];
                return item;
            }
        }
    }
}
