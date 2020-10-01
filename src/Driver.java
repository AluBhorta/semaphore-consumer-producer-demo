import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Driver {
    public static void main(String[] args) {
        int bufferSize;
        int sleepDurationInSecs;
        int numOfConsumers;
        int numOfProducers;
        int produceIntervalInSecs;
        int consumeIntervalInSecs;

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter buffer size (int): ");
        bufferSize = scanner.nextInt();
        System.out.print("Enter sleep duration in secs (int): ");
        sleepDurationInSecs = scanner.nextInt();
        System.out.print("Enter num of Consumers (int): ");
        numOfConsumers = scanner.nextInt();
        System.out.print("Enter num of Producers (int): ");
        numOfProducers = scanner.nextInt();
        System.out.print("Enter produce interval in secs (int): ");
        produceIntervalInSecs = scanner.nextInt();
        System.out.print("Enter consume interval in secs (int): ");
        consumeIntervalInSecs = scanner.nextInt();

        System.out.println("Starting...\n");

        ExecutorService executorService = Executors.newCachedThreadPool();
        Buffer buffer = new Buffer(bufferSize);

        for (int i = 0; i < numOfProducers; i++) {
            executorService.submit(new Producer(buffer, produceIntervalInSecs));
        }
        for (int i = 0; i < numOfConsumers; i++) {
            executorService.submit(new Consumer(buffer, consumeIntervalInSecs));
        }

        try {
            Thread.sleep(sleepDurationInSecs * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }

        System.exit(0);
    }
}
