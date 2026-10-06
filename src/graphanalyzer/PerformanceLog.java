package graphanalyzer;

import java.util.ArrayList;
import java.util.List;

/** Stores immutable descriptions of measurements for the current application session. */
public class PerformanceLog {
    private final List<String> rows = new ArrayList<>();

    public void record(String description, SearchResult result) {
        rows.add(description + System.lineSeparator() + "  " + result);
    }

    public void record(String description, Graph.TraversalResult result) {
        rows.add(description + System.lineSeparator() + "  " + result);
    }

    public void display() {
        if (rows.isEmpty()) System.out.println("No recorded searches or traversals yet.");
        for (int i = 0; i < rows.size(); i++) System.out.println((i + 1) + ". " + rows.get(i));
    }
}