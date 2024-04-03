package queue;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public class ArrayQueue extends AbstractQueue {
    private Object[] elements = new Object[2];
    private int head = 0;


    private Object[] extensionQueue(int newSize) {
        Object[] result = new Object[newSize];
        for (int i = 0; i < elements.length; i++) {
            result[i] = elements[(i + head) % elements.length];
        }
        head = 0;
        return result;
    }

    protected void implEnqueue(final Object element) {
        elements[(head + size) % elements.length] = Objects.requireNonNull(element);
        if (size + 1 == elements.length) {
            elements = extensionQueue(elements.length * 2);
        }
    }


    protected Object implElement() {
        return elements[head];
    }


    @Override
    protected Object implDeque() {
        Object result = elements[head];
        elements[head] = null;
        head = (head + 1) % elements.length;
        return result;
    }


    protected void implClear() {
        elements = new Object[2];
        head = 0;
    }

    // P: element != null && this != null
    // Q: (head' = (head - 1 ) % elements.length || head' = 0) && size' = size + 1 &&
    // immutable(head, size)

    public void push(Object element) {
        Objects.requireNonNull(element);
        if ((head - 1 + elements.length) % elements.length == (head + size) % elements.length) {
            elements = extensionQueue(elements.length * 2);
        }
        head = (head - 1 + elements.length) % elements.length;
        elements[head] = element;
        size++;
    }

    // P: size > 0 && this != null
    // Q: R = elements[(head + size - 1) % elements.length &&
    // immutable(head, size) &&
    // head' = head && size' = size
    public Object peek() {
        return elements[(head + size - 1) % elements.length];
    }

    // P: size > 0 && this != null
    // Q: R = elements[(head + size) % elements.length && head' = head &&
    // size' = size-- && immutable(head, size')
    public Object remove() {
        assert size > 0;
        size--;
        Object res = elements[(head + size) % elements.length];
        elements[(head + size) % elements.length] = null;
        return res;

    }

    // P: predicate != null && this != null
    // Q: R = a.length:
    // a = [element_i1...element_in]: predicate(test, element_ik) == true
    public int countIf(Predicate<Object> predicate) {
        Objects.requireNonNull(predicate);
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (predicate.test(elements[(i + head) % elements.length])) {
                count++;
            }
        }
        return count;
    }

    protected Queue implFlatMap(Function<Object, List<Object>> function) {
        ArrayQueue returnQueue = new ArrayQueue();
        for (int i = 0; i < size; i++) {
            List<Object> args = (function.apply(elements[(head + i) % elements.length]));
            for (Object arg : args) {
                returnQueue.enqueue(arg);
            }
        }
        return returnQueue;
    }
}
