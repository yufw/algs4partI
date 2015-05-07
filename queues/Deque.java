import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by yufw on 2015/5/5.
 */
public class Deque<Item> implements Iterable<Item> {
    private Node first, last;
    private int dequeSize;
    private class Node {
        Item item;
        Node next;
        Node prev;
    }
    public Deque() {
        first = null;
        last = null;
        dequeSize = 0;
    }
    public boolean isEmpty() {
        return size() == 0;
    }
    public int size() {
        return dequeSize;
    }
    public void addFirst(Item item) {
        if (item == null)
            throw new NullPointerException();
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.prev = null;
        first.next = oldfirst;
        if (isEmpty()) {
            last = first;
        } else {
            oldfirst.prev = first;
        }
        dequeSize++;
    }
    public void addLast(Item item) {
        if (item == null)
            throw new NullPointerException();
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldlast;
        if (isEmpty()) {
            first = last;
        } else {
            oldlast.next = last;
        }
        dequeSize++;
    }
    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException();
        Item item = first.item;
        first = first.next;
        dequeSize--;
        if (isEmpty()) {
            last = null;
        } else {
            first.prev = null;
        }
        return item;
    }
    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();
        Item item = last.item;
        last = last.prev;
        dequeSize--;
        if (isEmpty()) {
            first = null;
        }
        else {
            last.next = null;
        }

        return item;
    }
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        public void remove() {
            throw new UnsupportedOperationException();
        }
        public Item next() {
            if (current == null)
                throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
        public boolean hasNext() {
            return current != null;
        }
    }
    public static void main(String[] args) {
        Deque<Integer> d = new Deque<Integer>();
        System.out.println(d.isEmpty());
        d.addLast(4);
        d.addFirst(3);
        d.addFirst(5);
        d.addLast(9);
        for (int i : d)
            System.out.println(i);
        System.out.println("-------------");
        System.out.println(d.removeFirst());
        System.out.println(d.removeLast());
        System.out.println("-------------");
        for (int i : d)
            System.out.println(i);
        System.out.println(d.removeFirst());
        System.out.println(d.removeLast());
        System.out.println(d.isEmpty());
    }
}
