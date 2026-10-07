package graphanalyzer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Initial graph storage, vertex/edge validation and adjacency-list display. */
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

}