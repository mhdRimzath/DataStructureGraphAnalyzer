package graphanalyzer;

/** Array-backed stack. top == -1 means empty. */
public class IntStack {
    private final int[] values;
    private int top = -1;

    public IntStack(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("Capacity must be positive.");
        values = new int[capacity];
    }

    public void push(int value) {
        if (top == values.length - 1) throw new IllegalStateException("Stack is full.");
        values[++top] = value;
    }

    public int pop() {
        if (top == -1) throw new IllegalStateException("Stack is empty.");
        return values[top--];
    }

    public int peek() {
        if (top == -1) throw new IllegalStateException("Stack is empty.");
        return values[top];
    }

    public String toString() {
        StringBuilder text = new StringBuilder("Top -> [");
        for (int i = top; i >= 0; i--) {
            if (i < top) text.append(", ");
            text.append(values[i]);
        }
        return text.append("]").toString();
    }
}