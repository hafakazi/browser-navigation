import java.util.Iterator;
import java.util.NoSuchElementException;

// Custom circular dynamic array.

public class BrowserArrayList<T> implements Iterable<T> {
    private Object[] data = new Object[8];
    private int head = 0; // logical start
    private int size = 0;

    private int cap() {
        return data.length;
    }

    private void ensure(int need) {
        if (need <= cap()) {
            return;
        }

        int ncap = Math.max(need, cap() * 2);
        Object[] nd = new Object[ncap];

        for (int i = 0; i < size; i++) {
            nd[i] = data[(head + i) % cap()];
        }

        data = nd;
        head = 0;
    }

    public void addLast(T item) {
        ensure(size + 1);
        int idx = (head + size) % cap();
        data[idx] = item;
        size++;
    }

    @SuppressWarnings("unchecked")
    public T removeFirst() {
        if (size == 0) 
        {
            throw new NoSuchElementException("List is empty");
        }

        T val = (T) data[head];
        data[head] = null;
        head = (head + 1) % cap();
        size--;
        return val;
    }

    @SuppressWarnings("unchecked")
    public T get(int i) {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException();
        }

        return (T) data[(head + i) % cap()];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        for (int i=0;i<data.length;i++) {
            data[i]=null;
            head=0;
            size=0;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int i = 0;
            
            public boolean hasNext() {
                return i < size;
            }
            
            @SuppressWarnings("unchecked")
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                
                T v = (T) data[(head + i) % cap()];
                i++;
                return v;
            }
        };
    }
}
