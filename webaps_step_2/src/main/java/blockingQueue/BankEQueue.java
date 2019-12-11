package blockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BankEQueue {
    public static void main(String[] args) {
        BlockingQueue<Integer> bankQueue = new ArrayBlockingQueue<>(50);
        Producer producer = new Producer(bankQueue);
        Consumer consumer = new Consumer(bankQueue);

        new Thread(producer).start();
        new Thread(consumer).start();
    }
}
