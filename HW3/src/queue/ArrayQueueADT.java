package queue;

import java.util.Objects;
import java.util.function.Predicate;

public class ArrayQueueADT {
    private Object[] elements = new Object[2];
    private int head = 0;
    private int size = 0;

    // Model: a[1]..a[head]..a[tail]..a[n]
    // Invariant: for i=1..size: elements[(i + head) % elements.length] != null

    // immutable(head, size): for i=0..size: elements'[i + head] = elements[i + head]

    // P: element != null && queue != null
    // Q: (head' = head || head' = 0) && size' = size + 1 &&
    // immutable(head, size)
    public static void enqueue(final ArrayQueueADT queue, final Object element) {
        Objects.requireNonNull(element);
        queue.elements[(queue.head + queue.size) % queue.elements.length] = element;
        if (queue.size + 1 == queue.elements.length) {
            queue.elements = extensionQueue(queue,queue.elements.length * 2);
        }
        queue.size++;
    }

    private static Object[] extensionQueue(final ArrayQueueADT queue, int newSize) {
        Object[] result = new Object[newSize];
        for (int i = 0; i < queue.elements.length; i++) {
            result[i] = queue.elements[(i +queue.head) % queue.elements.length];
        }
        queue.head = 0;
        return result;
    }

    // P: size > 0 && queue != null
    // Q: R = elements[head] && immutable(head, size) &&
    // head' = head && size' = size
    public static Object element(final ArrayQueueADT queue) {
        assert queue.size > 0;
        return queue.elements[queue.head];
    }

    // P: size > 0 && queue != null
    // Q: R = elements[head] && head' = (head + 1) % elements.length &&
    // size' = size-- && immutable(head', size')
    public static Object dequeue(final ArrayQueueADT queue) {
        Object result = queue.elements[queue.head];
        queue.elements[queue.head] = null;
        queue.head = (queue.head + 1) % queue.elements.length;
        queue.size--;
        return result;
    }

    // P: queue != null
    // Q: R == size && immutable(start, size)
    public static int size(final ArrayQueueADT queue) {
        return queue.size;
    }

    // P: queue != null
    // Q: R == (size == 0) && immutable(start, size)
    public static boolean isEmpty(final ArrayQueueADT queue) {
        return (queue.size == 0);
    }

    // P: queue != null
    // Q: head' = 0 && size' = 0 && elements = new Object[2]
    public static void clear(final ArrayQueueADT queue) {
        queue.elements = new Object[2];
        queue.head = 0;
        queue.size = 0;
    }

    // P: element != 0 && queue != null
    // Q: (head' = (head - 1 ) % elements.length || head' = 0) && size' = size + 1 &&
    // immutable(head, size)

    public static void push(final ArrayQueueADT queue, Object element) {
        Objects.requireNonNull(element);
        if ((queue.head - 1 + queue.elements.length) %
                queue.elements.length ==
                (queue.head + queue.size) % queue.elements.length) {
            queue.elements = extensionQueue(queue,queue.elements.length * 2);
        }
        queue.head = (queue.head - 1 + queue.elements.length) % queue.elements.length;
        queue.elements[queue.head] = element;
        queue.size++;
    }

    // P: size > 0 && queue != null
    // Q: R = elements[(head + size - 1) % elements.length &&
    // immutable(head, size) &&
    // head' = head && size' = size
    public static Object peek(final ArrayQueueADT queue) {
        assert queue.size > 0;
        return queue.elements[(queue.head +queue.size - 1) % queue.elements.length];
    }

    // P: size > 0 && queue != null
    // Q: R = elements[(head + size) % elements.length && head' = head &&
    // size' = size-- && immutable(head, size')
    public static Object remove(final ArrayQueueADT queue) {
        assert queue.elements.length > 0;
        queue.size--;
        Object res = queue.elements[(queue.head + queue.size) % queue.elements.length];
        queue.elements[(queue.head + queue.size) % queue.elements.length] = null;
        return res;

    }

    // P: predicate != 0 && size > 0 && queue != null
    // Q: R = count : maximum by switching on
    // a = [element_i1...element_in]: predicate(test, element_ik) == true
    public static int countIf(final ArrayQueueADT queue, Predicate<Object> predicate) {
        assert predicate != null;
        int count = 0;
        for (int i = 0; i < queue.size; i++) {
            if (predicate.test(queue.elements[(i + queue.head) % queue.elements.length])) {
                count++;
            }
        }
        return count;
    }
}
