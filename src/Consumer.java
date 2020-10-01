import java.util.Date;

public class Consumer implements Runnable {
    private Buffer buffer;
    private int intervalInMs;

    public Consumer(Buffer buffer, int consumeIntervalInSecs) {
        this.buffer = buffer;
        this.intervalInMs = consumeIntervalInSecs * 1000;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Integer item = this.buffer.removeItem();
                this.consume(item);
                Thread.sleep(this.intervalInMs);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void consume(Integer _item) {
        System.out.println("Consuming item " + _item + " @ " + new Date().toString());
    }
}
