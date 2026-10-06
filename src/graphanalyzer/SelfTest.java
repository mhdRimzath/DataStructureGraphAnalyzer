package graphanalyzer;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/** Run independently from Main; no JUnit or external dependencies needed. */
public class SelfTest {
    private static int checks;

    private static void check(boolean condition, String message) {
        checks++;
        if (!condition) throw new AssertionError(message);
    }

    private static void rejects(Runnable operation, String message) {
        try { operation.run(); }
        catch (IllegalArgumentException | IllegalStateException expected) { checks++; return; }
        throw new AssertionError(message);
    }

    public static void main(String[] args) {
        arrayTests();
        stackAndQueueTests();
        linkedListTests();
        searchTests();
        graphTests();
        System.out.println("PASS: " + checks + " checks.");
    }

    private static void arrayTests() {
        IntArray array = new IntArray(50);
        List<Integer> expected = new ArrayList<>();
        Random random = new Random(300);
        rejects(() -> array.delete(0), "Empty array delete");
        rejects(() -> array.insert(-1, 7), "Negative index");
        rejects(() -> array.insert(1, 7), "Index beyond size");
        for (int i = 0; i < 1000; i++) {
            if (expected.isEmpty() || (expected.size() < 50 && random.nextBoolean())) {
                int position = random.nextInt(expected.size() + 1);
                int value = random.nextInt(100) - 50;
                array.insert(position, value);
                expected.add(position, value);
            } else {
                int position = random.nextInt(expected.size());
                check(array.delete(position) == expected.remove(position), "Array deletion result");
            }
            check(array.toString().equals(expected.toString()), "Array sequence");
        }
        IntArray full = new IntArray(1);
        full.insert(0, 5);
        rejects(() -> full.insert(1, 6), "Full array insert");
        check(full.toString().equals("[5]"), "Full insert leaves data intact");
        int[] copy = full.toArray();
        copy[0] = 99;
        check(full.toArray()[0] == 5, "Array snapshot must be a copy");
    }

    private static void stackAndQueueTests() {
        IntStack stack = new IntStack(2);
        rejects(stack::pop, "Empty stack pop");
        rejects(stack::peek, "Empty stack peek");
        stack.push(10); stack.push(20);
        rejects(() -> stack.push(30), "Stack overflow");
        check(stack.peek() == 20 && stack.pop() == 20 && stack.pop() == 10, "LIFO and peek");
        stack.push(-5);
        check(stack.pop() == -5, "Stack reuse");

        IntQueue queue = new IntQueue(5);
        ArrayDeque<Integer> expected = new ArrayDeque<>();
        rejects(queue::dequeue, "Empty queue dequeue");
        rejects(queue::peek, "Empty queue peek");
        for (int i = 0; i < 5; i++) { queue.enqueue(i); expected.add(i); }
        rejects(() -> queue.enqueue(99), "Queue overflow");
        for (int i = 5; i < 1000; i++) {
            check(queue.peek() == expected.peek(), "Queue peek");
            check(queue.dequeue() == expected.remove(), "FIFO across wraparound");
            queue.enqueue(i); expected.add(i);
        }
        while (!expected.isEmpty()) check(queue.dequeue() == expected.remove(), "Queue drain");
        check(queue.isEmpty(), "Queue empty after drain");
        queue.enqueue(17);
        check(queue.dequeue() == 17, "Queue reuse after empty");
    }

    private static void linkedListTests() {
        IntLinkedList list = new IntLinkedList();
        check(!list.delete(1) && list.search(1).index == -1, "Empty linked list");
        list.insertLast(10); list.insertLast(20); list.insertLast(20); list.insertLast(30);
        check(list.search(20).index == 1 && list.search(20).steps == 2, "List search first occurrence");
        check(list.delete(20) && list.toString().equals("[10, 20, 30]"), "Delete first duplicate");
        check(list.delete(10), "Delete head");
        check(list.delete(30), "Delete tail");
        list.insertLast(40);
        check(list.toString().equals("[20, 40]"), "Tail repaired before append");
        check(list.delete(20) && list.delete(40), "Delete down to empty");
        list.insertLast(50);
        check(list.toString().equals("[50]"), "List reuse after empty");
    }

    private static void searchTests() {
        int[] data = {10, 20, 30, 40, 50};
        check(SearchAlgorithms.linearSearch(data, 50).steps == 5, "Linear example steps");
        check(SearchAlgorithms.binarySearch(data, 50).steps == 3, "Binary example steps");
        check(SearchAlgorithms.linearSearch(new int[0], 1).steps == 0, "Empty linear search");
        check(SearchAlgorithms.binarySearch(new int[0], 1).index == -1, "Empty binary search");
        int[] duplicates = {Integer.MIN_VALUE, -1, -1, 0, 2, 2, Integer.MAX_VALUE};
        int[] targets = {Integer.MIN_VALUE, -3, -1, 0, 1, 2, 3, Integer.MAX_VALUE};
        for (int target : targets) {
            SearchResult a = SearchAlgorithms.linearSearch(duplicates, target);
            SearchResult b = SearchAlgorithms.binarySearch(duplicates, target);
            boolean exists = Arrays.binarySearch(duplicates, target) >= 0;
            check((a.index >= 0) == exists && (b.index >= 0) == exists, "Search presence agrees");
            if (exists) check(duplicates[a.index] == target && duplicates[b.index] == target, "Returned indices valid");
        }
        check(Arrays.equals(data, new int[] {10, 20, 30, 40, 50}), "Search input unchanged");
    }

    private static void graphTests() {
        Graph graph = new Graph();
        rejects(() -> graph.bfs("A"), "BFS on missing vertex");
        rejects(() -> graph.addVertex(" "), "Blank vertex");
        for (String name : new String[] {"A", "B", "C", "D", "E", "Z"}) graph.addVertex(name);
        rejects(() -> graph.addVertex("A"), "Duplicate vertex");
        rejects(() -> graph.addEdge("A", "Missing"), "Missing edge endpoint");
        rejects(() -> graph.addEdge("A", "A"), "Self-loop");
        graph.addEdge("A", "B"); graph.addEdge("A", "C");
        graph.addEdge("B", "D"); graph.addEdge("C", "E");
        rejects(() -> graph.addEdge("B", "A"), "Duplicate reverse edge");
        check(graph.bfs("A").order.toString().equals("[A, B, C, D, E]"), "BFS order");
        check(graph.dfs("A").order.toString().equals("[A, B, D, C, E]"), "DFS order");
        check(graph.bfs("A").steps() == 13 && graph.dfs("A").steps() == 13, "Graph step counts");
        check(graph.bfs("Z").steps() == 1 && graph.dfs("Z").order.size() == 1, "Isolated vertex");
        graph.addEdge("D", "E");
        check(graph.bfs("A").order.size() == 5 && graph.dfs("A").order.size() == 5, "Cycles terminate without revisiting");
        check(graph.bfs("A").steps() == 15 && graph.dfs("A").steps() == 15, "Cyclic graph counts");
        check(graph.bfs("E").order.contains("A"), "Undirected connectivity");
    }
}