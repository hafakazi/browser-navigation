import java.util.EmptyStackException;
import java.util.Iterator;

// Stack using BrowserLinkedList; top is at the head.

public class BrowserStack<T> implements Iterable<T> {
    private final BrowserLinkedList<T> list = new BrowserLinkedList<>();

    public void push(T item) {
        list.addFirst(item);
    }

    public T pop() {
        try {
            return list.removeFirst();
        } catch (Exception e) {
            throw new EmptyStackException();
        }
    }

    public T peek() {
        try {
            return list.peekFirst();
        }
        catch (Exception e) {
            throw new EmptyStackException();
        }
    }

    public boolean isEmpty() {
        return list.size() == 0;
    }

    public int size() {
        return list.size();
    }

    public void clear() {
        list.clear();
    }

    // Iterate top → bottom (used by StackIterator).

    @Override
    public Iterator<T> iterator() {
        return list.descendingIterator();
    }
}
