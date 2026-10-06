package graphanalyzer;

import java.util.Arrays;

/** Fixed-capacity array: only positions 0 through size - 1 hold data. */
public class IntArray {
    private final int[] values;
    private int size;

    public IntArray(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("Capacity must be positive.");
        values = new int[capacity];
    }

    public int size() { return size; }

    public void insert(int index, int value) {
        if (size == values.length) throw new IllegalStateException("Array is full.");
        if (index < 0 || index > size) throw new IllegalArgumentException("Invalid insertion index.");
        // Move elements right, starting at the end so no value is overwritten.
        for (int i = size; i > index; i--) values[i] = values[i - 1];
        values[index] = value;
        size++;
    }

    public int[] toArray() { return Arrays.copyOf(values, size); }
    public String toString() { return Arrays.toString(toArray()); }
}