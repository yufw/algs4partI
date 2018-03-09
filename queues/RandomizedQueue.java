import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by yufw on 2015/5/5.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] rq;
    private int N = 0;

    public RandomizedQueue() {
        rq = (Item[]) new Object[1];
    }
    public boolean isEmpty() {
        return size() == 0;
    }
    public int size() {
        return N;
    }
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        if (N == rq.length)
            resize(2 * rq.length);
        rq[N++] = item;
    }
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();
        int ind = StdRandom.uniform(0, N);
        Item item = rq[ind];
        rq[ind] = rq[--N];
        rq[N] = null;
        if (N > 0 && N == rq.length/4)
            resize(rq.length / 2);
        return item;
    }
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException();
        int ind = StdRandom.uniform(0, N);
        Item item = rq[ind];
        return item;
    }
    public Iterator<Item> iterator() {
        return new RandomizedArrayIterator();
    }
    private void resize(int capacity) {
        Item[] copy = (Item []) new Object[capacity];
        for (int i = 0; i < N; i++)
            copy[i] = rq[i];
        rq = copy;
    }
    private class RandomizedArrayIterator implements Iterator<Item> {
        private int current = 0;
        private int[] arr;

        {
            arr = new int[N];
            for (int i = 0; i < arr.length; i++)
                arr[i] = i;
            StdRandom.shuffle(arr);
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
        public Item next() {
            if (current == N)
                throw new NoSuchElementException();
            int ind = arr[current++];
            Item item = rq[ind];
            return item;
        }
        public boolean hasNext() {
            return current != N;
        }
    }
    public static void main(String[] args) {
        RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();
        System.out.println(q.isEmpty());
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        q.enqueue(4);
        q.enqueue(5);
        System.out.println(q.size());
        for (int i : q)
            System.out.println(i);
        System.out.println(q.sample());
        System.out.println(q.dequeue());
        System.out.println(q.dequeue());
        System.out.println(q.size());
    }
}
