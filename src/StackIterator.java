import java.util.Iterator;

// Iterator that goes through the BrowserStack from top to bottom without modifying it.

public class StackIterator<T> implements Iterator<T> {
    private final Iterator<T> inner;

    public StackIterator(BrowserStack<T> stack) {
        this.inner = stack.iterator();
    }
    
    public boolean hasNext() {
        return inner.hasNext();
    }
    
    public T next() {
        return inner.next();
    }
}
