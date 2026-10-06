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

    /** Precondition: values are sorted in ascending order. Does not sort or mutate input. */
    public static SearchResult binarySearch(int[] values, int target) {
        long start = System.nanoTime();
        int low = 0;
        int high = values.length - 1;
        int steps = 0;
        while (low <= high) {
            int middle = low + (high - low) / 2;
            steps++;
            if (values[middle] == target) return new SearchResult(middle, steps, System.nanoTime() - start);
            if (values[middle] < target) low = middle + 1;
            else high = middle - 1;
        }
        return new SearchResult(-1, steps, System.nanoTime() - start);
    }
}