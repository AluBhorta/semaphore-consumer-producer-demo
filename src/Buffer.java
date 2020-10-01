

public class Buffer {
    private int size;
    private Integer[] items;
    private int fullSemaphore;
    private int emptySemaphore;
    private int mutexSemaphore;
    private int in = 0;
    private int out = 0;

    public Buffer(int size) {
        this.size = size;
        this.items = new Integer[size];
        this.fullSemaphore = 0;
        this.emptySemaphore = size;
        this.mutexSemaphore = 1;
    }

    public boolean insertItem(Integer item) {
        while (this.emptySemaphore <= 0 || this.mutexSemaphore == 0)
            ;

        waitEmpty();
        waitMutex();

        System.out.println("Inserting new item to index: " + in);
        this.items[in] = item;
        in = (in + 1) % size;

        signalMutex();
        signalFull();

        return true;
    }

    public Integer removeItem() {
        while (this.fullSemaphore <= 0 || this.mutexSemaphore == 0){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        waitFull();
        waitMutex();

        Integer item = this.items[out];
        out = (out + 1) % size;
        System.out.println("Removing an item from index: " + out);

        signalMutex();
        signalEmpty();

        return item;
    }

    private synchronized void waitMutex() {
        this.mutexSemaphore--;
    }

    private synchronized void signalMutex() {
        this.mutexSemaphore++;
    }

    private synchronized void waitEmpty() {
        this.emptySemaphore--;
    }

    private synchronized void signalEmpty() {
        this.emptySemaphore++;
    }

    private synchronized void waitFull() {
        this.fullSemaphore--;
    }

    private synchronized void signalFull() {
        this.fullSemaphore++;
    }
}
