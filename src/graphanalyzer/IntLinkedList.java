package graphanalyzer;

/** Singly linked list with a tail pointer for constant-time append. */
public class IntLinkedList {
    private static class Node {
        private final int value;
        private Node next;
        private Node(int value) { this.value = value; }
    }

    private Node head;
    private Node tail;

    public void insertLast(int value) {
        Node node = new Node(value);
        if (head == null) head = node;
        else tail.next = node;
        tail = node;
    }

    /** Removes the first occurrence; returns false if the value is absent. */
    public boolean delete(int value) {
        Node previous = null;
        Node current = head;
        while (current != null) {
            if (current.value == value) {
                if (previous == null) head = current.next;
                else previous.next = current.next;
                if (current == tail) tail = previous;
                return true;
            }
            previous = current;
            current = current.next;
        }
        return false;
    }

    public SearchResult search(int target) {
        long start = System.nanoTime();
        int index = 0;
        int steps = 0;
        for (Node current = head; current != null; current = current.next) {
            steps++;
            if (current.value == target) {
                return new SearchResult(index, steps, System.nanoTime() - start);
            }
            index++;
        }
        return new SearchResult(-1, steps, System.nanoTime() - start);
    }

    public String toString() {
        StringBuilder text = new StringBuilder("[");
        for (Node current = head; current != null; current = current.next) {
            if (current != head) text.append(", ");
            text.append(current.value);
        }
        return text.append("]").toString();
    }
}