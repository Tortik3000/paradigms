package queue;

import java.util.List;
import java.util.function.Function;

public interface Queue {
    int size();

    boolean isEmpty();

    Object element();

    void clear();

    void enqueue(final Object element);

    Object dequeue();

    Queue flatMap(Function<Object, List<Object>> function);
}
