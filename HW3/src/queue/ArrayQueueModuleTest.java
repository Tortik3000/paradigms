package queue;

import java.util.Objects;
import java.util.function.Predicate;

public class ArrayQueueModuleTest {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            ArrayQueueModule.enqueue(i + "e");
        }
        for (int i = 0; i < 5; i++) {
            ArrayQueueModule.push(i);
        }
        Predicate<Object> predicate = i -> i.toString().contains("e1");
        System.out.println(ArrayQueueModule.countIf(predicate));
        for (int i = 0; i < 8; i++) {
            final Object value = ArrayQueueModule.remove();
            System.out.println(ArrayQueueModule.size() + " " + value);
        }
        System.out.println(ArrayQueueModule.element());
        System.out.println(ArrayQueueModule.peek());
        ArrayQueueModule.clear();

        for (int i = 0; i < 3; i++) {
            ArrayQueueModule.enqueue("e" + (i + 5));
        }

        while (!ArrayQueueModule.isEmpty()) {
            final Object value = ArrayQueueModule.dequeue();
            System.out.println(ArrayQueueModule.size() + " " + value);
        }
    }
}
