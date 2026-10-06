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

    public String toString() {
        StringBuilder text = new StringBuilder("[");
        for (Node current = head; current != null; current = current.next) {
            if (current != head) text.append(", ");
            text.append(current.value);
        }
        return text.append("]").toString();
    }
}