package graphanalyzer;

/** Initial smoke checks. Run this class separately from Main. */
public class SelfTest {
    private static int checks;

    private static void check(boolean condition, String message) {
        checks++;
        if (!condition) throw new AssertionError(message);
    }

    public static void main(String[] args) {
        IntArray array = new IntArray(5);
        array.insert(0, 10);
        array.insert(1, 30);
        array.insert(1, 20);
        check(array.toString().equals("[10, 20, 30]"), "Array insertion");
        check(array.delete(1) == 20, "Array deletion");

        IntStack stack = new IntStack(3);
        stack.push(10);
        stack.push(20);
        check(stack.pop() == 20 && stack.pop() == 10, "Stack LIFO");

        IntQueue queue = new IntQueue(3);
        queue.enqueue(10);
        queue.enqueue(20);
        check(queue.dequeue() == 10 && queue.dequeue() == 20, "Queue FIFO");

        IntLinkedList list = new IntLinkedList();
        list.insertLast(10);
        list.insertLast(20);
        check(list.search(20).index == 1, "List search");
        check(list.delete(10) && list.toString().equals("[20]"), "List deletion");

        int[] values = {10, 20, 30, 40, 50};
        check(SearchAlgorithms.linearSearch(values, 50).steps == 5, "Linear steps");
        check(SearchAlgorithms.binarySearch(values, 50).steps == 3, "Binary steps");
        System.out.println("PASS: " + checks + " smoke checks.");
    }
}