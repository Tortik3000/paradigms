package queue;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class LinkedQueue extends AbstractQueue {

    private Node head;
    private Node tail;


    protected void implEnqueue(final Object element) {
        if (size == 0) {
            tail = new Node(Objects.requireNonNull(element));
            head = tail;
        } else {
            tail.next = new Node(Objects.requireNonNull(element));
            tail = tail.next;
        }
    }


    protected Object implElement() {
        return head.element;
    }

    @Override
    protected Object implDeque() {
        Object result = head.element;
        head = head.next;
        return result;
    }


    protected void implClear() {
        tail = null;
        head = null;
    }


    protected Queue implFlatMap(Function<Object, List<Object>> function) {
        LinkedQueue returnQueue = new LinkedQueue();
        Node next = head;
        for (int i = 0; i < size; i++) {
            List<Object> args = (function.apply(next.element));
            for (Object arg : args) {
                returnQueue.enqueue(arg);
            }
            next = next.next;
        }
        return returnQueue;
    }

    private static class Node {
        private final Object element;
        private Node next;

        public Node(Object element) {
            this.element = element;
        }
    }
}
