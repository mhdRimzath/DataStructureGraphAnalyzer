package graphanalyzer;

/** One search measurement; a step is one inspected array element/list node. */
public class SearchResult {
    public final int index;
    public final int steps;
    public final long nanoseconds;

    public SearchResult(int index, int steps, long nanoseconds) {
        this.index = index;
        this.steps = steps;
        this.nanoseconds = nanoseconds;
    }

    public String toString() {
        String outcome = index < 0 ? "Not found" : "Found at index " + index;
        return outcome + " | inspected elements/nodes: " + steps + " | time: " + nanoseconds + " ns";
    }
}