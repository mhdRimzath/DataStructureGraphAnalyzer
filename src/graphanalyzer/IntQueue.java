package graphanalyzer;

/** Circular queue: freed array positions can be reused. */
public class IntQueue {
    private final int[] values;
    private int front;
    private int size;

    public IntQueue(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("Capacity must be positive.");
        values = new int[capacity];
    }

    public boolean isEmpty() { return size == 0; }

    public void enqueue(int value) {
        if (size == values.length) throw new IllegalStateException("Queue is full.");
        int rear = (front + size) % values.length;
        values[rear] = value;
        size++;
    }

    public int dequeue() {
        if (isEmpty()) throw new IllegalStateException("Queue is empty.");
        int removed = values[front];
        front = (front + 1) % values.length;
        size--;
        return removed;
    }

}