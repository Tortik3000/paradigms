package queue;

public class ArrayQueueADTTest {
    public static void main(String[] args){
        ArrayQueueADT queue1 = new ArrayQueueADT();
        ArrayQueueADT queue2 = new ArrayQueueADT();

        for (int i = 0; i < 5; i++) {
            ArrayQueueADT.enqueue(queue1, "q_1 e_" + i);
            ArrayQueueADT.enqueue(queue2, "q_2 e_" + i);

        }
        for (int i = 0; i < 4; i++) {
            Object value = ArrayQueueADT.dequeue(queue1);
            System.out.println(ArrayQueueADT.size(queue1) + " " + value);

            value = ArrayQueueADT.dequeue(queue2);
            System.out.println(ArrayQueueADT.size(queue2) + " " + value);
        }
        System.out.println(ArrayQueueADT.element(queue1));
        ArrayQueueADT.clear(queue1);

        for (int i = 0; i < 3; i++) {
            ArrayQueueADT.enqueue(queue1,"e" + (i + 5));
        }

        while(!ArrayQueueADT.isEmpty(queue1)) {
            final Object value = ArrayQueueADT.dequeue(queue1);
            System.out.println(ArrayQueueADT.size(queue1) + " " + value);
        }
    }

}
