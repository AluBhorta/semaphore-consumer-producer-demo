import java.util.Date;
import java.util.Random;

public class Producer implements Runnable {
    private Buffer buffer;
    private int intervalInMS;

    public Producer(Buffer buffer, int produceIntervalInSecs) {
        this.buffer = buffer;
        this.intervalInMS = produceIntervalInSecs * 1000;
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.buffer.insertItem(this.produce());
                Thread.sleep(this.intervalInMS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Integer produce() {
        Integer item = new Random().nextInt();
        System.out.println("Producing item " + item + " @ " + new Date().toString());
        return item;
    }
}
