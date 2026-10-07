package graphanalyzer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Simple, undirected, unweighted adjacency-list graph. Labels are case-sensitive. */
public class Graph {
    private static final int MAX_VERTICES = 200;
    private final Map<String, Integer> ids = new LinkedHashMap<>();
    private final List<String> labels = new ArrayList<>();
    private final List<Set<Integer>> adjacency = new ArrayList<>();

    public void addVertex(String label) {
        if (label == null || label.trim().isEmpty()) throw new IllegalArgumentException("Vertex name cannot be blank.");
        label = label.trim();
        if (label.length() > 40) throw new IllegalArgumentException("Use at most 40 characters for a vertex.");
        if (ids.containsKey(label)) throw new IllegalArgumentException("Vertex already exists.");
        if (labels.size() == MAX_VERTICES) throw new IllegalStateException("Graph limit is 200 vertices.");
        ids.put(label, labels.size());
        labels.add(label);
        adjacency.add(new LinkedHashSet<>());
    }

    private int requireVertex(String label) {
        Integer id = ids.get(label == null ? null : label.trim());
        if (id == null) throw new IllegalArgumentException("Vertex does not exist: " + label);
        return id;
    }

    public void addEdge(String from, String to) {
        int a = requireVertex(from);
        int b = requireVertex(to);
        if (a == b) throw new IllegalArgumentException("Self-loops are not allowed.");
        if (adjacency.get(a).contains(b)) throw new IllegalArgumentException("Edge already exists.");
        adjacency.get(a).add(b);
        adjacency.get(b).add(a);
    }

    public boolean isEmpty() { return labels.isEmpty(); }

    public TraversalResult bfs(String startLabel) {
        int startVertex = requireVertex(startLabel);
        long start = System.nanoTime();
        boolean[] visited = new boolean[labels.size()];
        IntQueue queue = new IntQueue(labels.size());
        List<String> order = new ArrayList<>();
        int examined = 0;
        visited[startVertex] = true; // Mark on enqueue: each vertex enters the queue once.
        queue.enqueue(startVertex);
        while (!queue.isEmpty()) {
            int current = queue.dequeue();
            order.add(labels.get(current));
            for (int neighbour : adjacency.get(current)) {
                examined++;
                if (!visited[neighbour]) {
                    visited[neighbour] = true;
                    queue.enqueue(neighbour);
                }
            }
        }
        return new TraversalResult(order, examined, System.nanoTime() - start);
    }

    public TraversalResult dfs(String startLabel) {
        int startVertex = requireVertex(startLabel);
        long start = System.nanoTime();
        boolean[] visited = new boolean[labels.size()];
        List<String> order = new ArrayList<>();
        int examined = visit(startVertex, visited, order);
        return new TraversalResult(order, examined, System.nanoTime() - start);
    }

    private int visit(int current, boolean[] visited, List<String> order) {
        visited[current] = true;
        order.add(labels.get(current));
        int examined = 0;
        for (int neighbour : adjacency.get(current)) {
            examined++;
            if (!visited[neighbour]) examined += visit(neighbour, visited, order);
        }
        return examined;
    }

    public String toString() {
        if (isEmpty()) return "Graph is empty.";
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < labels.size(); i++) {
            List<String> neighbours = new ArrayList<>();
            for (int neighbour : adjacency.get(i)) neighbours.add(labels.get(neighbour));
            text.append(labels.get(i)).append(" -> ").append(neighbours).append(System.lineSeparator());
        }
        return text.toString();
    }

    public static class TraversalResult {
        public final List<String> order;
        public final int examinedNeighbours;
        public final long nanoseconds;

        private TraversalResult(List<String> order, int examinedNeighbours, long nanoseconds) {
            this.order = java.util.Collections.unmodifiableList(new ArrayList<>(order));
            this.examinedNeighbours = examinedNeighbours;
            this.nanoseconds = nanoseconds;
        }

        public int steps() { return order.size() + examinedNeighbours; }

        public String toString() {
            return "Order: " + order + " | vertices visited: " + order.size()
                + " | adjacency entries examined: " + examinedNeighbours
                + " | steps: " + steps() + " | time: " + nanoseconds + " ns";
        }
    }
}