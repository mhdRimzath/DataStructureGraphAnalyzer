package graphanalyzer;

/** Initial search outcome: match position and inspected-element count. */
public class SearchResult {
    public final int index;
    public final int steps;

    public SearchResult(int index, int steps) {
        this.index = index;
        this.steps = steps;
    }

    public String toString() {
        String outcome = index < 0 ? "Not found" : "Found at index " + index;
        return outcome + " | inspected elements/nodes: " + steps;
    }
}