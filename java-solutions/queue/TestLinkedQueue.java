package queue;

public class TestLinkedQueue {


    public static void main(String[] args) {
        LinkedQueue queue1 = new LinkedQueue();

        for (int i = 0; i < 5; i++) {
            queue1.enqueue("q_1 e_" + i);
        }
        for (int i = 0; i < 4; i++) {
            Object value = queue1.dequeue();
            System.out.println(queue1.size() + " " + value);


        }
        System.out.println(queue1.element());
        queue1.clear();
        System.out.println(queue1.isEmpty());

        for (int i = 0; i < 3; i++) {
            queue1.enqueue("e" + (i + 5));
        }

        while (!queue1.isEmpty()) {
            final Object value = queue1.dequeue();
            System.out.println(queue1.size() + " " + value);
        }
    }
}
