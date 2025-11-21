import java.util.Iterator;
import java.util.NoSuchElementException;

// Simple custom generic doubly linked list.

 public class BrowserLinkedList<T> implements Iterable<T> {
    private static class Node<T> {
        T item;
        Node<T> prev, next;

        Node(T item) { 
            this.item = item; 
        }
    }

    private Node<T> head; // top first
    private Node<T> tail; // last
    private int size;

    public void addFirst(T item) {
        Node<T> n = new Node<>(item);

        if (head == null) {
            head = tail = n;
        } else {
            n.next = head; 
            head.prev = n; 
            head = n; 
        }

        size++;
    }

    public T removeFirst() {
        if (head == null) {
            throw new NoSuchElementException("List is empty");
        }

        T val = head.item;
        head = head.next;

        if (head != null) {
            head.prev = null;
        } else {
            tail = null;
        }

        size--;
        return val;
    }

    public T peekFirst() {
        if (head == null) {
            throw new NoSuchElementException("List is empty");
        }

        return head.item;
    }

    public void addLast(T item) {
        Node<T> n = new Node<>(item);

        if (tail == null) {
            head = tail = n;
        } else {
            tail.next = n;
            n.prev = tail;
            tail = n;
        }

        size++;
    }

    public T removeLast() {
        if (tail == null) {
            throw new NoSuchElementException("List is empty");
        }

        T val = tail.item;
        tail = tail.prev;

        if (tail != null) {
            tail.next = null;
        } else {
            head = null;
        }

        size--;
        return val;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void clear() {
        head = tail = null;
        size = 0;
    }

    // Iterate head → tail (top → bottom for the stack).

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Node<T> cur = head;
            
            public boolean hasNext() {
                return cur != null;
            }

            public T next() {
                if (cur == null) 
                {
                    throw new NoSuchElementException();
                }

                T v = cur.item;
                cur = cur.next;
                return v;
            }
        };
    }

    // Used by BrowserStack to iterate top → bottom.

    Iterator<T> descendingIterator() {
        return iterator();
    }
}
