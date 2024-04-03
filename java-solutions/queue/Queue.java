package queue;

import java.util.List;
import java.util.function.Function;

public interface Queue {

    // Model: a[1]..a[head]..a[tail]..a[n]
    // Invariant: for i=1..size: elements[(i + head) % elements.length] != null

    // immutable(head, size): for i=0..size: elements'[i + head] = elements[i + head]



    // P: this != null
    // Q: R == size && immutable(start, size)
    int size();

    // P: this != null
    // Q: R == (size == 0) && immutable(start, size)
    boolean isEmpty();

    // P: size > 0 && this != null
    // Q: R = elements[head] && immutable(head, size) &&
    // head' = head && size' = size
    Object element();

    // P: this != null
    // Q: head' = 0 && size' = 0 && elements = new Object[2]
    void clear();

    // P: element != null && this != null
    // Q: (head' = head || head' = 0) && size' = size + 1 &&
    // immutable(head, size)
    void enqueue(final Object element);

    // P: size > 0 && this != null
    // Q: R = elements[head] && head' = (head + 1) % elements.length &&
    // size' = size-- && immutable(head', size')
    Object dequeue();

    // P: function != null && this != null
    // Q: for i=0..k == size: function(a[i]) = bi
    // R = [b1[0]..b1[n1]..bk[0]..bk[nk]]
    Queue flatMap(Function<Object, List<Object>> function);
}
