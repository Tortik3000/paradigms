package queue;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public abstract class AbstractQueue implements Queue{
    protected int size;


    public int size() {
        return size;
    }


    public boolean isEmpty() {
        return (size == 0);
    }


    public Object element() {
        assert size > 0;
        return implElement();
    }


    public void clear() {
        size = 0;
        implClear();
    }


    public Object dequeue() {
        assert size > 0;
        size--;
        return implDeque();
    }


    public void enqueue(final Object element) {
        implEnqueue(element);
        size++;
    }

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
