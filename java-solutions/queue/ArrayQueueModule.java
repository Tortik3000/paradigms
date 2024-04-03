package queue;

import java.util.Objects;
import java.util.function.Predicate;

public class ArrayQueueModule {
    private static Object[] elements = new Object[2];
    private static int head = 0;
    private static int size = 0;

    // Model: a[1]..a[head]..a[tail]..a[n]
    // Invariant: for i=1..size: elements[(i + head) % elements.length] != null

    // immutable(head, size): for i=0..size: elements'[i + head] = elements[i + head]

    // P: element != null
    // Q: (head' = head || head' = 0) && size' = size + 1 &&
    // immutable(head, size)
    public static void enqueue(final Object element) {
        Objects.requireNonNull(element);
        elements[(head + size) % elements.length] = element;
        if (size + 1 == elements.length) {
            elements = extensionQueue(elements.length * 2);
        }
        size++;
    }

    private static Object[] extensionQueue(int newSize) {
        Object[] result = new Object[newSize];
        for (int i = 0; i < elements.length; i++) {
            result[i] = elements[(i + head) % elements.length];
        }
        head = 0;
        return result;
    }

    // P: size > 0
    // Q: R = elements[head] && immutable(head, size) &&
    // head' = head && size' = size
    public static Object element() {
        assert size > 0;
        return elements[head];
    }

    // P: size > 0
    // Q: R = elements[head] && head' = (head + 1) % elements.length &&
    // size' = size-- && immutable(head', size')
    public static Object dequeue() {
        Object result = elements[head];
        elements[head] = null;
        head = (head + 1) % elements.length;
        size--;
        return result;
    }

    // P: true
    // Q: R == size && immutable(start, size)
    public static int size() {
        return size;
    }

    // P: true
    // Q: R == (size == 0) && immutable(start, size)
    public static boolean isEmpty() {
        return (size == 0);
    }

    // P: true
    // Q: head' = 0 && size' = 0 && elements = new Object[2]
    public static void clear() {
        elements = new Object[2];
        head = 0;
        size = 0;
    }

    // P: element != 0
    // Q: (head' = (head - 1 ) % elements.length || head' = 0) && size' = size + 1 &&
    // immutable(head, size)

    public static void push(Object element) {
        Objects.requireNonNull(element);
        if ((head - 1 + elements.length) % elements.length == (head + size) % elements.length) {
            elements = extensionQueue(elements.length * 2);
        }
        head = (head - 1 + elements.length) % elements.length;
        elements[head] = element;
        size++;
    }

    // P: size > 0
    // Q: R = elements[(head + size - 1) % elements.length &&
    // immutable(head, size) &&
    // head' = head && size' = size
    public static Object peek() {
        assert size > 0;
        return elements[(head + size - 1) % elements.length];
    }

    // P: size > 0
    // Q: R = elements[(head + size) % elements.length && head' = head &&
    // size' = size-- && immutable(head, size')
    public static Object remove() {
        assert elements.length > 0;
        size--;
        Object res = elements[(head + size) % elements.length];
        elements[(head + size) % elements.length] = null;
        return res;

    }

    // P: predicate != 0 && size > 0
    // Q: R = count : maximum by switching on
    // a = [element_i1...element_in]: predicate(test, element_ik) == true
    public static int countIf(Predicate<Object> predicate) {
        Objects.requireNonNull(predicate);
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (predicate.test(elements[(i + head) % elements.length])) {
                count++;
            }
        }
        return count;
    }
}
