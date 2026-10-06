package graphanalyzer;

public final class SearchAlgorithms {
    private SearchAlgorithms() { }

    public static SearchResult linearSearch(int[] values, int target) {
        long start = System.nanoTime();
        int steps = 0;
        for (int i = 0; i < values.length; i++) {
            steps++;
            if (values[i] == target) return new SearchResult(i, steps, System.nanoTime() - start);
        }
        return new SearchResult(-1, steps, System.nanoTime() - start);
    }

}