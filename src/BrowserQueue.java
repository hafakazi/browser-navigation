import java.util.Iterator;
import java.util.NoSuchElementException;

// FIFO queue on top of BrowserArrayList (circular).

public class BrowserQueue<T> implements Iterable<T> {
    private final BrowserArrayList<T> arr = new BrowserArrayList<>();

    public void offer(T item) {
        arr.addLast(item);
    }

    public T poll() {
        try { 
            return arr.removeFirst();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public boolean isEmpty() {
        return arr.size() == 0;
    }

    public int size() {
        return arr.size();
    }

    public void clear() {
        arr.clear();
    }

    @Override
    public Iterator<T> iterator() {
        return arr.iterator();
    }
}
