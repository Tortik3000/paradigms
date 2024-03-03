package queue;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public abstract class AbstractQueue implements Queue{
    protected int size;

    // P: this != null
    // Q: R == size && immutable(start, size)
    public int size() {
        return size;
    }

    // P: this != null
    // Q: R == (size == 0) && immutable(start, size)
    public boolean isEmpty() {
        return (size == 0);
    }

    // P: size > 0 && this != null
    // Q: R = elements[head] && immutable(head, size) &&
    // head' = head && size' = size
    public Object element() {
        assert size > 0;
        return implElement();
    }

    // P: this != null
    // Q: head' = 0 && size' = 0 && elements = new Object[2]
    public void clear() {
        size = 0;
        implClear();
    }

    // P: size > 0 && this != null
    // Q: R = elements[head] && head' = (head + 1) % elements.length &&
    // size' = size-- && immutable(head', size')
    public Object dequeue() {
        assert size > 0;
        size--;
        return implDeque();
    }

    // P: element != null && this != null
    // Q: (head' = head || head' = 0) && size' = size + 1 &&
    // immutable(head, size)
    public void enqueue(final Object element) {
        implEnqueue(element);
        size++;
    }

    // P: function != null && this != null
    // Q: R = [function(a1)..function(an)
    public Queue flatMap(Function<Object, List<Object>> function) {
        Objects.requireNonNull(function);
        return implFlatMap(function);
    }
    protected abstract Object implDeque();
    protected abstract void implEnqueue(final Object element);
    protected abstract Object implElement();
    protected abstract void implClear();
    protected abstract Queue implFlatMap(Function<Object, List<Object>> function);
}
